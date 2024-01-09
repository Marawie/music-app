package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.repository.PodcastRepository;
import music.musicapp.service.interfaceService.PodcastService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PodcastServiceImpl implements PodcastService {
    private final PodcastRepository podcastRepository;
}