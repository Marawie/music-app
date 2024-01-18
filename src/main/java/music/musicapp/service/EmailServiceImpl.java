package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.service.interfaceService.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static music.musicapp.model.user.ConfirmationState.EMAIL_VERIFICATION_NOT_ACCEPTED;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${spring.mail.host}")
    private String host;

    @Override
    public void sendMessageToUser(String name, String to, String token) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setSubject(String.valueOf(EMAIL_VERIFICATION_NOT_ACCEPTED));
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText("This is your validation link");
        javaMailSender.send(simpleMailMessage);
    }
}
