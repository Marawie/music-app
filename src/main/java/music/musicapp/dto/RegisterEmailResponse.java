package music.musicapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEmailResponse {

    @JsonProperty(value = "isClicked")
    private boolean IsClicked;
}