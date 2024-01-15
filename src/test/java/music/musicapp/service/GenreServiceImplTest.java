package music.musicapp.service;

import music.musicapp.dto.spotify.GenresResponse;
import music.musicapp.model.Genre;
import music.musicapp.repository.GenreRepository;
import music.musicapp.service.interfaceService.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

class GenreServiceImplTest {

    private GenreRepository genreRepository;
    private SpotifyServiceImpl spotifyService;
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        genreRepository = mock(GenreRepository.class);
        spotifyService = mock(SpotifyServiceImpl.class);
        genreService = new GenreServiceImpl(genreRepository, spotifyService);
    }

    @Test
    void createListOfGenresShouldUpdateGenres() {
        // Given
        String[] spotifyGenres = {"pop", "rock", "hip-hop"};
        GenresResponse genresResponse = new GenresResponse(spotifyGenres);

        List<Genre> existingGenres = Arrays.asList(new Genre("pop"), new Genre("jazz"));

        // When
        when(spotifyService.getGenres()).thenReturn(genresResponse);
        when(genreRepository.findAll()).thenReturn(existingGenres);

        genreService.createListOfGenres();

        // Then
        ArgumentCaptor<Set> savedGenresCaptor = ArgumentCaptor.forClass(Set.class);
        verify(genreRepository, times(1)).saveAll(savedGenresCaptor.capture());

        Set<Genre> savedGenres = savedGenresCaptor.getValue();

        assert savedGenres.size() == 3;

    }

    @Test
    void createListOfGenresShouldNotUpdateGenresIfNoNewGenres() {
        // Given
        String[] spotifyGenres = {"pop", "jazz"};
        GenresResponse genresResponse = new GenresResponse(spotifyGenres);

        List<Genre> existingGenres = Arrays.asList(new Genre("pop"), new Genre("jazz"));
        when(spotifyService.getGenres()).thenReturn(genresResponse);
        when(genreRepository.findAll()).thenReturn(existingGenres);

        // When
        genreService.createListOfGenres();

        // Then
        // Verify that saveAll is never called
        verify(genreRepository, times(1)).saveAll(any());
        assert existingGenres.size() == genresResponse.genres().length;
    }
}