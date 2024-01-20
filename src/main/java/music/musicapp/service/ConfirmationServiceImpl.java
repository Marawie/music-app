package music.musicapp.service;


import lombok.RequiredArgsConstructor;
import music.musicapp.dto.RegisterEmailResponse;
import music.musicapp.model.user.Confirmation;
import music.musicapp.service.interfaceService.ConfirmationService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationServiceImpl implements ConfirmationService {
    @Override
    public Confirmation UserEmailAccepted(RegisterEmailResponse response) {
        return null;
    }
}
