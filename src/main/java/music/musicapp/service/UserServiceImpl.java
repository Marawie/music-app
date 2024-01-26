package music.musicapp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import music.musicapp.dto.ChangePasswordRequest;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.user.User;
import music.musicapp.repository.UserRepository;
import music.musicapp.service.interfaceService.UserService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import static music.musicapp.exception.ExceptionEnum.CONFIRMATION_FAILED;
import static music.musicapp.model.user.ConfirmationState.*;
import static music.musicapp.model.user.ConfirmationState.EMAIL_VERIFICATION_EXPIRED;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    //Zrobić 2FA możliwość
    //1. Ty musisz zadbać o tym żeby użytkownik wlączył możliwośćl używania 2FA (enable 2FA)
    //2. Ty mu musisz wygenerować QRCODE z trzema danymi: nazwa Aplikacji, googleSecretCode, email.
    //3. GoogleSecretCode generowany za pomocą biblioteka getTOTPCode().
    //4. 10 kodów zapasowych w przypadku utraty do GoogleAuthenticator
    //5. Musisz to zapisać w bazie wraz z kodami zapasowymi (każdy kod jednorazowy)
    //6. On musi zaakceptować że wszystko zapisał i sparował urzadzenie z qrcode.
    //7. Jak on to zrobi, to musi sie wlaczyc 2FA.
    //8. Kiedy on loguje, ty sprawdzasz, ze 2FA jest wlaczone, i jesli tak, to wysylasz response z wymaganiem codu 2fa od Authentikator
    //9. Ze ten endpoint z podaniem kodu og autentykatora nie ma byc ogolnie dostepny
    //10. Musi byc utworzonu temporary token ktory jest przydatny tylko pod endpoint z podaniem kodu zapsawego lud kodu z authentykatora
    //11. Jesli podal dobry kod, to zwrocic accessTokena

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = userRepository.findByEmail(connectedUser.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RestException(ExceptionEnum.PASSWORD_NOT_MATCHES);
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new RestException(ExceptionEnum.PASSWORD_ARE_NOT_THE_SAME);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void userEmailAcceptingLink(Long id, String token) throws MessagingException {

        final User user = userRepository.findById(id).orElseThrow(
                () -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        if (handleConfirmationClick(token)) {
            sendConfirmationEmail(user.getEmail(), "Registration confirmation required");

        } else {
            userRepository.save(user);
            sendConfirmationEmail(user.getEmail(), "Registration confirmation unaccepted");
        }
    }

    @Override
    public boolean handleConfirmationClick(String token) {

        final User user = userRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        if (!user.getConfirmationState().equals(EMAIL_VERIFICATION_ACCEPTED)) {
            user.setConfirmationState(EMAIL_VERIFICATION_REQUIRED);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void userAcceptedLink(Long id, String token) {

        final User user = userRepository.findById(id)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        if (!user.getConfirmationState().equals(EMAIL_VERIFICATION_ACCEPTED) &&
                !user.getConfirmationState().equals(EMAIL_VERIFICATION_EXPIRED)){
            user.setConfirmationState(EMAIL_VERIFICATION_ACCEPTED);
            userRepository.save(user);
        }
        else
            throw new RestException(CONFIRMATION_FAILED);
    }

    @Scheduled(cron = "0 0 1 * * ?") // 1am
    public void userLinkExpired() throws MessagingException {
        List<User> unacceptedUsers = userRepository.findByConfirmation_ConfirmationState(
                EMAIL_VERIFICATION_REQUIRED);

        for (User user : unacceptedUsers) {
            LocalDateTime localDateTime = user.getLocalDateTime();
            if (localDateTime.plusDays(7).isBefore(LocalDateTime.now())) {
                user.setConfirmationState(EMAIL_VERIFICATION_EXPIRED);
                userRepository.delete(user);
                sendConfirmationEmail(user.getEmail(), "Your registration confirmation has expired, please register again!");
            }
        }
    }

    private void sendConfirmationEmail(String to, String subject) throws MessagingException {

        final User user = userRepository.findByEmail(to)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        final String token = user.getToken();
        final String confirmationLink = "http://localhost:8080/confirm/" + user.getId() + "/confirm?token=" + token;

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlContent = "<p>The message was generated for a user with the name " + user.getFirstname() + " " + user.getLastname() + "</p>" +
                "<p>Click the link below to confirm your registration:</p>" +
                "<a href=\"" + confirmationLink + "\">Confirm Registration</a>" +
                "<p>The message with the link is only valid for 7 days, after which your account will expire. If this happens otherwise, please report it to our support.</p>";

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom(user.getEmail());

        javaMailSender.send(mimeMessage);
    }
}