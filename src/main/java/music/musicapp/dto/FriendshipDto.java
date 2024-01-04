package music.musicapp.dto;

import lombok.*;
import music.musicapp.model.user.FriendshipRequestState;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipDto {
    private String username;
    private String friendName;
    private FriendshipRequestState state;
}