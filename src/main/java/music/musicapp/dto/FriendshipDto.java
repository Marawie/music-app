package music.musicapp.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipDto {
    private UserDto user;
    private UserDto friend;

}