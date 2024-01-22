package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.service.interfaceService.ConfirmationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequiredArgsConstructor
public class ConfirmationController {
    private final ConfirmationService confirmationService;


    @GetMapping("/confirm/{id}")
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