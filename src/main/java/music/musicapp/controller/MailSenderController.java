package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.service.interfaceService.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("user/panel/")
public class MailSenderController {
    private final UserService userService;

    @GetMapping("/confirm/{token}")
    public ResponseEntity<String> confirmedRegistrationByUser(@RequestParam String token) {
        userService.userAcceptedLink(token);
        return ResponseEntity.ok("Account confirmed successfully!");
    }

    @GetMapping("/confirm/reminder/{token}")
    public ResponseEntity<String> confirmedRegistrationByUserReminder(@RequestParam String token) {
        userService.userReminderEmail(token);
        return ResponseEntity.ok("Reminder email was send!");
    }

    @GetMapping("/confirm/handle/{token}")
    public ResponseEntity<String> handleReminderConfirmationAccountByEmail(@RequestParam String token) {
        userService.handleConfirmationClick(token);
        return ResponseEntity.ok("Account confirmed successfully!");
    }
}
