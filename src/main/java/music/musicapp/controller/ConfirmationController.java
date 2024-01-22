package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.service.interfaceService.ConfirmationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class ConfirmationController {
    private final ConfirmationService confirmationService;


    @GetMapping("/confirm/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public String confirmRegistration(@PathVariable Long id, @RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        boolean confirmationResult = confirmationService.userEmailAccepted(id,token);

        if (confirmationResult) {
            return "redirect:/user/panel/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Error during acceptance request.");
            return "redirect:/exception/token-expired";
        }
    }
}