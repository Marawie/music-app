package music.musicapp.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipDto {
    private String username;
    private String friendName;
    private String state;
}