package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.repository.MusicRepository;
import music.musicapp.service.interfaceService.MusicService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl {
    private final MusicRepository musicRepository;
    private final MusicService musicService;
}
