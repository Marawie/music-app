package music.musicapp.service.interfaceService;

public interface ConfirmationService {
    void userLinkExpired();
    boolean userEmailAccepted(Long id, String token);
    boolean handleConfirmationClick(String token);
}