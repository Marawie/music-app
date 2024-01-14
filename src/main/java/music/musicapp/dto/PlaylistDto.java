package music.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import music.musicapp.model.Music;
import music.musicapp.model.user.User;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDto {
    private Long id;
    private boolean isPrivate;
    private String name;
    private User user;
    private Set<Music> musics;
}