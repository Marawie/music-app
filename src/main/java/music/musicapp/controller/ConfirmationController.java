package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.service.interfaceService.ConfirmationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequiredArgsConstructor
public class ConfirmationController {
    private final ConfirmationService confirmationService;


    @GetMapping("/confirm")
    public String confirmRegistration(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        boolean confirmationResult = confirmationService.handleConfirmationClick(token);

        if (confirmationResult) {
            return "redirect:/user/panel/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Błąd podczas potwierdzania.");
            return "redirect:/exception/token-expired";
        }
    }
}