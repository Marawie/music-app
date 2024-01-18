package music.musicapp.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ConfirmationState {
    EMAIL_VERIFICATION_ACCEPTED("Your account has been activated"),
    EMAIL_VERIFICATION_NOT_ACCEPTED("You need to activate your account"),
    EMAIL_VERIFICATION_EXPIRED("Your link verification has been expired"),
    ;


    @Getter
    String message;
}
