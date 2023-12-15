package music.musicapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import music.musicapp.model.user.User;

import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("email")
    private String email;

    @JsonProperty("friendships")
    private Set<User> userSet;
}
