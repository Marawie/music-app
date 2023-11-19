package music.musicapp.service.interfaceService;

import music.musicapp.model.Music;

import java.util.Optional;

public interface AlbumMusicService {
    Optional<Music> findAlbumMusicByMusicTitle();
}
