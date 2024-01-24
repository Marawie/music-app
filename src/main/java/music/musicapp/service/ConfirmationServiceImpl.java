package music.musicapp.service;


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
    public boolean userEmailAccepted(Long id, String token) {


        final User user = userRepository.findById(id).orElseThrow(
                () -> new RestException(ExceptionEnum.USER_NOT_FOUND));
        System.out.println(user.getConfirmation().getConfirmationState());
        if (handleConfirmationClick(token)) {
            sendConfirmationEmail(user.getEmail(), "Registration confirmation accepted");
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
        System.out.println(user.getConfirmation().getToken());
        Confirmation confirmation = user.getConfirmation();

        if (confirmation != null && !confirmation.getConfirmationState().equals(EMAIL_VERIFICATION_ACCEPTED)) {
            confirmation.setConfirmationState(EMAIL_VERIFICATION_ACCEPTED);
            confirmationRepository.save(confirmation);
            return true;
        } else {
            return false;
        }
    }

    @Scheduled(cron = "0 0 1 * * ?") // 1am
    public void userLinkExpired() {
        List<User> unacceptedUsers = userRepository.findByConfirmation_ConfirmationState(
                EMAIL_VERIFICATION_NOT_ACCEPTED);

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

    private void sendConfirmationEmail(String to, String subject) {

        final User user = userRepository.findByEmail(to) // przyjerzec się temu czy to zadziała zgodnie z oczekiwaniami
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        final SimpleMailMessage message = new SimpleMailMessage();
        final String token = user.getConfirmation().getToken();
        final String confirmationLink = host + user.getId() + "/confirm?token=" + token;

        message.setTo(to);
        message.setFrom(fromEmail);
        message.setSubject(subject);
        message.setText("The message was generated for a user with the nickname " + user.getFirstname() + user.getLastname() +
                ". If this happens otherwise, please report it to our support.\n" +
                " Click the link below to confirm your registration:\n" + confirmationLink +
                " \nThe message with the link is only valid for 7 days, after which your account will expire");

        javaMailSender.send(message);
    }
}
