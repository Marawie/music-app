package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.repository.PlaylistRepository;
import music.musicapp.service.interfaceService.PlaylistService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl {
    private final PlaylistRepository playlistRepository;
    private final PlaylistService playlistService;
}
