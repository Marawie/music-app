package music.musicapp.dto;

import lombok.Builder;
import lombok.Data;
import music.musicapp.model.Music;
import music.musicapp.model.user.User;

import java.util.Set;

@Data
@Builder
public class PlaylistDto {
    private boolean isPrivate;
    private User user;
    private Set<Music> musics;
}