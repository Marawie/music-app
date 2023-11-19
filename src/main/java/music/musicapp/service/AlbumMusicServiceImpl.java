package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.model.Music;
import music.musicapp.repository.AlbumMusicRepository;
import music.musicapp.service.interfaceService.AlbumMusicService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AlbumMusicServiceImpl implements AlbumMusicService{
    private final AlbumMusicRepository albumMusicRepository;

    @Override
    public Optional<Music> findAlbumMusicByMusicTitle() {
        return Optional.empty();
    }
}
