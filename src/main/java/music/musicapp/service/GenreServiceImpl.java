package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.spotify.GenresResponse;
import music.musicapp.model.Genre;
import music.musicapp.repository.GenreRepository;
import music.musicapp.service.interfaceService.GenreService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final SpotifyServiceImpl spotifyService;

    @Override
    @Scheduled(fixedDelay = 3600000)
    public void createListOfGenres() {
        final GenresResponse genres = spotifyService.getGenres();
        final List<Genre> existedGenres = genreRepository.findAll();
        final Set<Genre> genreSet = Arrays.stream(genres.genres())
                .map(Genre::new)
                .collect(Collectors.toSet());
        if (!Collections.disjoint(existedGenres, genreSet)) {
            existedGenres.forEach(genreSet::remove);
        }
        genreRepository.saveAll(genreSet);
    }
}
