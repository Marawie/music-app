package music.musicapp.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
}
