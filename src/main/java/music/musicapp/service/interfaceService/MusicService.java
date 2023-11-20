package music.musicapp.service.interfaceService;

import music.musicapp.model.Music;
import music.musicapp.model.Playlist;

import java.util.Optional;

public interface MusicService {
    Optional<Music> addMusicWhichIsLikedToPlaylist(Playlist playlist);
}
