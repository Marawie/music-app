package music.musicapp.service.interfaceService;

import music.musicapp.model.Music;
import music.musicapp.model.Playlist;

import java.util.Optional;

public interface MusicService {

    // TODO: 06.01.2024 zastanwoic sie nad metodami  
    Optional<Music> addMusicWhichIsLikedToPlaylist(Playlist playlist);
}
