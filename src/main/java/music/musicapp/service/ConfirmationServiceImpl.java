package music.musicapp.service;


import lombok.RequiredArgsConstructor;
import music.musicapp.auth.AuthenticationService;
import music.musicapp.dto.ConfirmationDto;
import music.musicapp.dto.RegisterEmailResponse;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.user.ConfirmationState;
import music.musicapp.model.user.User;
import music.musicapp.repository.ConfirmationRepository;
import music.musicapp.repository.UserRepository;
import music.musicapp.service.interfaceService.ConfirmationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public ConfirmationDto userEmailAccepted(Long id, RegisterEmailResponse response) {
        final ModelMapper modelMapper = new ModelMapper();

        final User user = userRepository.findById(id).orElseThrow(
                () -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        LocalDateTime localDateTime = user.getConfirmation().getLocalDateTime();

        if (response.isIsClicked()) {

            user.getConfirmation().setConfirmationState(ConfirmationState.EMAIL_VERIFICATION_ACCEPTED);
            confirmationRepository.save(user.getConfirmation());
            sendConfirmationEmail(user.getEmail(), "Registration confirmation accepted", user);
        } else {
            user.getConfirmation().setConfirmationState(ConfirmationState.EMAIL_VERIFICATION_NOT_ACCEPTED);
            confirmationRepository.save(user.getConfirmation());
            sendConfirmationEmail(user.getEmail(), "Registration confirmation unaccepted", user);
        }
        return modelMapper.map(user.getConfirmation(), ConfirmationDto.class);
    }

    @Scheduled(cron = "0 0 1 * * ?") // 1am
    public void userEmailExpired() {
        List<User> unacceptedUsers = userRepository.findByConfirmation_ConfirmationStateUsers(
                ConfirmationState.EMAIL_VERIFICATION_NOT_ACCEPTED);

        for (User user : unacceptedUsers) {
            LocalDateTime localDateTime = user.getConfirmation().getLocalDateTime();
            if (localDateTime.plusDays(7).isBefore(LocalDateTime.now())) {
                user.getConfirmation().setConfirmationState(ConfirmationState.EMAIL_VERIFICATION_EXPIRED);
                userRepository.delete(user);
                confirmationRepository.save(user.getConfirmation());
                sendConfirmationEmail(user.getEmail(), "Your registration confirmation has expired, please register again!", user);
            }
        }
    }


    private void sendConfirmationEmail(String to, String subject, User user) {
        final String confirmationLink = host + "/confirm?token=" + authenticationService.generateTokenToEmail(user);
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setFrom(fromEmail);
        message.setSubject(subject);
        message.setText("Click the link below to confirm your registration:\n" + confirmationLink);
        message.setText("The message with the link is only valid for 7 days, after which your account will expire");

        javaMailSender.send(message);
    }
}
