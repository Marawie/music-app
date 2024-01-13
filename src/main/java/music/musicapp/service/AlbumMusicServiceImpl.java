package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.repository.AlbumMusicRepository;
import music.musicapp.service.interfaceService.AlbumMusicService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AlbumMusicServiceImpl implements AlbumMusicService {
    private final AlbumMusicRepository albumMusicRepository;

}