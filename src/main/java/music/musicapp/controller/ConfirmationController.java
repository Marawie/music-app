package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.ConfirmationDto;
import music.musicapp.dto.RegisterEmailResponse;
import music.musicapp.service.interfaceService.ConfirmationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConfirmationController {
    private final ConfirmationService confirmationService;


    @PutMapping("/confirmations/{userId}")
    public ConfirmationDto UserEmailAccepted(@RequestBody RegisterEmailResponse response, @PathVariable Long UserId){
        return confirmationService.UserEmailAccepted(UserId, response);
    }
}