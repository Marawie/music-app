package music.musicapp.service.interfaceService;

import jakarta.mail.MessagingException;

public interface ConfirmationService {
    void userLinkExpired() throws MessagingException;

    void userEmailAcceptingLink(Long id, String token) throws MessagingException;

    boolean handleConfirmationClick(String token);

    void userAcceptedLink(Long id, String token);

}