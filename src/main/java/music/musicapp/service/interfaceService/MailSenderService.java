package music.musicapp.service.interfaceService;

import jakarta.mail.MessagingException;

public interface MailSenderService {
    void sendConfirmationEmail(String to, String subject) throws MessagingException;
}
