package music.musicapp.components;

import org.springframework.stereotype.Component;

@Component
public class EmailContentBuilder {

    public String buildConfirmationEmail(String userName, String confirmationLink) {
        return "<p>The message was generated for a user with the name " + userName + "</p>" +
                "<p>Click the link below to confirm your registration:</p>" +
                "<a href=\"" + confirmationLink + "\">Confirm Registration</a>" +
                "<p>The message with the link is only valid for 7 days, after which your account will expire. If this happens otherwise, please report it to our support.</p>";
    }
}