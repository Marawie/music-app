package music.musicapp.service.interfaceService;

import music.musicapp.dto.ConfirmationDto;

public interface ConfirmationService {
    void userEmailExpired();
    ConfirmationDto userEmailAccepted(Long id);
    boolean handleConfirmationClick(String token);
}