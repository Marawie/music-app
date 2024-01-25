package music.musicapp.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import music.musicapp.service.interfaceService.ConfirmationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConfirmationController {
    private final ConfirmationService confirmationService;

    @GetMapping("/confirm-request/{id}")
    public void confirmRegistrationLink(@PathVariable Long id, @RequestParam("token") String token) throws MessagingException {
        confirmationService.userEmailAcceptingLink(id, token);
    }

    @GetMapping("/confirm/{id}/{token}")
    @PermitAll
    public ResponseEntity<String> confirmedRegistrationByUser(@PathVariable Long id, @PathVariable String token) {
        confirmationService.userAcceptedLink(id, token);
        return ResponseEntity.ok("Account confirmed successfully");
    }
}