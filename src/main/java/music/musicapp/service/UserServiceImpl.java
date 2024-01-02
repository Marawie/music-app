package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.repository.*;
import music.musicapp.service.interfaceService.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final GenreRepository genreRepository;
    private final MusicRepository musicRepository;
    private final PodcastRepository podcastRepository;
    private final AuthorRepository authorRepository;


    @Override
    public List<Object> globalSearch(String query) {
        final List<Object> results = new ArrayList<>();

        results.addAll(genreRepository.findByNameContaining(query));
        results.addAll(musicRepository.findByTitleContaining(query));
        results.addAll(podcastRepository.findByNameContaining(query));
        results.addAll(authorRepository.findByNameContaining(query));

        return results;
    }

}

