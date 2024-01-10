package music.musicapp.service.interfaceService;

import music.musicapp.dto.PlaylistDto;
import music.musicapp.model.Music;

public interface MusicService {
     PlaylistDto addMusicToPlaylist(Music music, Long id);
}
