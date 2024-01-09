package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.MusicDto;
import music.musicapp.repository.AlbumMusicRepository;
import music.musicapp.service.interfaceService.AlbumMusicService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AlbumMusicServiceImpl implements AlbumMusicService {
    private final AlbumMusicRepository albumMusicRepository;


    @Override
    public Optional<MusicDto> findAlbumMusicByMusicTitle() {
        return Optional.empty();
    }
}
