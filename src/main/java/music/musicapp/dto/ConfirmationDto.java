package music.musicapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import music.musicapp.model.user.ConfirmationState;
import music.musicapp.model.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ConfirmationDto {
    private LocalDateTime localDateTime;
    private ConfirmationState confirmationState;
    private String token;
    private User user;
}