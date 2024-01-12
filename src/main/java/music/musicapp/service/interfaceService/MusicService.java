package music.musicapp.service.interfaceService;

import music.musicapp.dto.PlaylistDto;
import music.musicapp.model.Music;

import java.security.Principal;

public interface MusicService {
     PlaylistDto addMusicToPlaylist(Principal principal, Music music, Long playlistId);
}
