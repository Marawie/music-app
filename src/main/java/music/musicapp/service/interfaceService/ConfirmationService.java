package music.musicapp.service.interfaceService;

import jakarta.mail.MessagingException;

public interface ConfirmationService {
    void userLinkExpired() throws MessagingException;
    boolean userEmailAccpetatingLink(Long id, String token) throws MessagingException;
    boolean handleConfirmationClick(String token);
}