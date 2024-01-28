package music.musicapp.components;

import org.springframework.stereotype.Component;

@Component
public class EmailContentBuilder {

    public String buildConfirmationEmail(String username, String confirmationLink) {
        return "<p>The message was generated for a user with the name " + username + "</p>" +
                "<p>Click the link below to confirm your registration:</p>" +
                "<a href=\"" + confirmationLink + "\">Confirm Registration</a>" +
                "<p>The message with the link is only valid for 7 days, after which your account will expire. If this happens otherwise, please report it to our support.</p>";
    }

    public String buildReminderEmail(String username, String confirmationLink) {
        return "<p>Dear " + username + ",</p>" +
                "<p>This is a reminder that you have not yet confirmed your registration.</p>" +
                "<p>Click the link below to complete the activation process:</p>" +
                "<a href=\"" + confirmationLink + "\">Activate Account</a>" +
                "<p>Please note that the activation link is valid for 7 days. If you have already activated your account or if you encounter any issues, please contact our support team.</p>";
    }
}