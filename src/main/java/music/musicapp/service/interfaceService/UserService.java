package music.musicapp.service.interfaceService;

import jakarta.mail.MessagingException;
import music.musicapp.dto.ChangePasswordRequest;

import java.security.Principal;

public interface UserService {
    void changePassword(ChangePasswordRequest request, Principal connectedUser);
    void userLinkExpired() throws MessagingException;

    void userEmailAcceptingLink(Long id, String token) throws MessagingException;

    boolean handleConfirmationClick(String token);

    void userAcceptedLink(Long id, String token);
}