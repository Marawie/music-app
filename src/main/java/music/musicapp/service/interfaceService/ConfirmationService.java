package music.musicapp.service.interfaceService;

import music.musicapp.dto.RegisterEmailResponse;
import music.musicapp.model.user.Confirmation;

public interface ConfirmationService {

    Confirmation UserEmailAccepted(RegisterEmailResponse response);
}
