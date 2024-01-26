package music.musicapp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import music.musicapp.components.EmailContentBuilder;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.user.User;
import music.musicapp.repository.UserRepository;
import music.musicapp.service.interfaceService.MailSenderService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final EmailContentBuilder emailContentBuilder;

    public void sendConfirmationEmail(String to, String subject) throws MessagingException {

        final User user = userRepository.findByEmail(to)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        final String token = user.getConfirmationToken();
        final String confirmationLink = String.format("http://localhost:8080/user/panel/confirm/" + user.getId() + "/confirm?token=" + token);

        String htmlContent = emailContentBuilder.buildConfirmationEmail(user.getFirstname() + " " + user.getLastname(), confirmationLink);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom(user.getEmail());

        javaMailSender.send(mimeMessage);
    }
}
