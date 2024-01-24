package music.musicapp.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import music.musicapp.service.interfaceService.ConfirmationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConfirmationController {
    private final ConfirmationService confirmationService;

    @GetMapping("/confirm/{id}")
    public void confirmRegistrationLink(@PathVariable Long id, @RequestParam("token") String token) throws MessagingException {
        confirmationService.userEmailAccpetatingLink(id, token);
    }
}