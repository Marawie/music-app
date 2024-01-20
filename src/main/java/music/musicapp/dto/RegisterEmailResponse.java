package music.musicapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterEmailResponse {

    @JsonProperty(value = "isClicked")
    private boolean IsClicked;
}