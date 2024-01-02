package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.SearchResultDto;
import music.musicapp.repository.AuthorRepository;
import music.musicapp.repository.GenreRepository;
import music.musicapp.repository.MusicRepository;
import music.musicapp.repository.PodcastRepository;
import music.musicapp.service.interfaceService.SearchService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final PodcastRepository podcastRepository;
    private final AuthorRepository authorRepository;
    private final MusicRepository musicRepository;
    private final GenreRepository genreRepository;


    @Override
    public SearchResultDto searchEngine(String query) {
        SearchResultDto searchResultDto = new SearchResultDto(
                musicRepository.findByTitleContaining(query),
                podcastRepository.findByNameContaining(query),
                genreRepository.findByNameContaining(query),
                authorRepository.findByNameContaining(query)
        );
        return searchResultDto;
    }
}
