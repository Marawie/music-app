package music.musicapp.service.interfaceService;

import music.musicapp.dto.ConfirmationDto;
import music.musicapp.dto.RegisterEmailResponse;

public interface ConfirmationService {

    void userEmailExpired();

    ConfirmationDto userEmailAccepted(Long id, RegisterEmailResponse response);
     boolean handleConfirmationClick(String token);

}
