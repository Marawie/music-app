package music.musicapp.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import music.musicapp.dto.AuthenticationRequest;
import music.musicapp.dto.AuthenticationResponse;
import music.musicapp.dto.RegisterRequest;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.token.Token;
import music.musicapp.model.token.TokenType;
import music.musicapp.model.user.ConfirmationState;
import music.musicapp.model.user.User;
import music.musicapp.repository.TokenRepository;
import music.musicapp.repository.UserRepository;
import music.musicapp.service.interfaceService.MailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static music.musicapp.exception.ExceptionEnum.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MailSenderService mailSenderService;

    @Value("${application.security.jwt.secret-key}")
    private String jwtSecret;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .sex(request.getSex())
                .confirmationState(ConfirmationState.EMAIL_VERIFICATION_REQUIRED)
                .confirmationToken(generateTokenToEmail())
                .dateThatUserCreateAccount(LocalDateTime.now())
                .build();

        User savedUser = repository.save(user);
        try {
            mailSenderService.sendConfirmationEmail(user.getEmail(), "Music-app");
        }
        catch (MessagingException e) {
            throw new RestException(USER_NOT_FOUND);
        }
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        if (user.getConfirmationState() == ConfirmationState.EMAIL_VERIFICATION_ACCEPTED){

            String jwtToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        else throw new RestException(ExceptionEnum.USER_CONFIRMATION_STATE_IS_NOT_ACCEPTED);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;

        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {

            User user = repository.findByEmail(userEmail)
                    .orElseThrow(() -> new RestException(USER_NOT_FOUND));

            if (jwtService.isTokenValid(refreshToken, user)) {

                String accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public String generateTokenToEmail() {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() * 1000);

        return Jwts.builder()
                .setSubject(String.valueOf(TokenType.ACCESS))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
}