package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.service.interfaceService.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("user/panel/")
public class MailSenderController {
    private final UserService userService;

    @GetMapping("/confirm/{id}/{token}")
    public ResponseEntity<String> confirmedRegistrationByUser(@PathVariable Long id, @PathVariable String token) {
        userService.userAcceptedLink(id, token);
        return ResponseEntity.ok("Account confirmed successfully");
    }
}
