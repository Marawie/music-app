package music.musicapp.service.interfaceService;

import music.musicapp.dto.ConfirmationDto;
import music.musicapp.dto.RegisterEmailResponse;
import music.musicapp.model.user.Confirmation;

public interface ConfirmationService {

    ConfirmationDto UserEmailAccepted(Long id, RegisterEmailResponse response);
}
