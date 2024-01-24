package music.musicapp.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import music.musicapp.auth.AuthenticationService;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.user.Confirmation;
import music.musicapp.model.user.User;
import music.musicapp.repository.ConfirmationRepository;
import music.musicapp.repository.UserRepository;
import music.musicapp.service.interfaceService.ConfirmationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static music.musicapp.model.user.ConfirmationState.*;

@Service
@RequiredArgsConstructor
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationRepository confirmationRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final AuthenticationService authenticationService;

    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${application.security.jwt.secret-key}")
    private String jwtSecret;

    @Transactional
    @Override
    public boolean userEmailAccpetatingLink(Long id, String token) throws MessagingException {


        final User user = userRepository.findById(id).orElseThrow(
                () -> new RestException(ExceptionEnum.USER_NOT_FOUND));
        if (handleConfirmationClick(token)) {
            sendConfirmationEmail(user.getEmail(), "Registration confirmation required");
            return true;

        } else {
            confirmationRepository.save(user.getConfirmation());
            sendConfirmationEmail(user.getEmail(), "Registration confirmation unaccepted");
            return false;
        }
    }

    @Override
    public boolean handleConfirmationClick(String token) {

        final User user = userRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));
        Confirmation confirmation = user.getConfirmation();

        if (confirmation != null && !confirmation.getConfirmationState().equals(EMAIL_VERIFICATION_ACCEPTED)) {
            confirmation.setConfirmationState(EMAIL_VERIFICATION_REQUIRED);
            confirmationRepository.save(confirmation);
            return true;
        } else {
            return false;
        }
    }

    @Scheduled(cron = "0 0 1 * * ?") // 1am
    public void userLinkExpired() throws MessagingException {
        List<User> unacceptedUsers = userRepository.findByConfirmation_ConfirmationState(
                EMAIL_VERIFICATION_REQUIRED);

        for (User user : unacceptedUsers) {
            LocalDateTime localDateTime = user.getConfirmation().getLocalDateTime();
            if (localDateTime.plusDays(7).isBefore(LocalDateTime.now())) {
                user.getConfirmation().setConfirmationState(EMAIL_VERIFICATION_EXPIRED);
                confirmationRepository.save(user.getConfirmation());
                userRepository.delete(user);
                sendConfirmationEmail(user.getEmail(), "Your registration confirmation has expired, please register again!");
            }
        }
    }

    private void sendConfirmationEmail(String to, String subject) throws MessagingException {

        final User user = userRepository.findByEmail(to)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        final SimpleMailMessage message = new SimpleMailMessage();
        final String token = user.getConfirmation().getToken();
        final String confirmationLink = "http://localhost:8080/" + user.getId() + "/confirm?token=" + token;

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlContent = "<p>The message was generated for a user with the name " + user.getFirstname() + " " + user.getLastname() + "</p>" +
                "<p>Click the link below to confirm your registration:</p>" +
                "<a href=\"" + confirmationLink + "\">Confirm Registration</a>" +
                "<p>The message with the link is only valid for 7 days, after which your account will expire. If this happens otherwise, please report it to our support.</p>";

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom(fromEmail);

        javaMailSender.send(mimeMessage);
        javaMailSender.send(message);
    }
}
