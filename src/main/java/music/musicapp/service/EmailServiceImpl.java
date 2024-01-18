package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.auth.AuthenticationService;
import music.musicapp.model.user.User;
import music.musicapp.service.interfaceService.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender javaMailSender;

    private final AuthenticationService authenticationService;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${spring.mail.host}")
    private String host;


    @Override
    public void sendMessageToUser(User user) {
        String token = authenticationService.generateTokenToEmail(user.getId());

        String confirmationLink = host + "/confirm?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Potwierdzenie rejestracji");
        message.setText("Kliknij poniższy link, aby potwierdzić rejestrację:\n" + confirmationLink);

        javaMailSender.send(message);
    }
    }

