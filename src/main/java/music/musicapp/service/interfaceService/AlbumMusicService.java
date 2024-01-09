package music.musicapp.service.interfaceService;

import music.musicapp.dto.MusicDto;

import java.util.Optional;

public interface AlbumMusicService {
    Optional<MusicDto> findAlbumMusicByMusicTitle();
}