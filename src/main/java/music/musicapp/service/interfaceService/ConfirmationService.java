package music.musicapp.service.interfaceService;

import music.musicapp.dto.ConfirmationDto;
import music.musicapp.dto.RegisterEmailResponse;

public interface ConfirmationService {

    ConfirmationDto UserEmailExpired(Long id, RegisterEmailResponse response);

    ConfirmationDto UserEmailAccepted(Long id, RegisterEmailResponse response);
}
