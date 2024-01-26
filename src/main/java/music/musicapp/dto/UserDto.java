package music.musicapp.dto;

import lombok.*;
import music.musicapp.model.Playlist;
import music.musicapp.model.user.ConfirmationState;
import music.musicapp.model.user.FriendshipRequestState;
import music.musicapp.model.user.User;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private Set<User> userSet;
    private FriendshipRequestState state;
    private Set<Playlist> playlists;
    private ConfirmationState confirmationState;
    private String token;
    private LocalDateTime localDateTime;
}