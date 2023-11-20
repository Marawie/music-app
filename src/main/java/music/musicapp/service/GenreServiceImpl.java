package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.repository.GenreRepository;
import music.musicapp.service.interfaceService.GenreService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService{
    private final GenreRepository genreRepository;
}
