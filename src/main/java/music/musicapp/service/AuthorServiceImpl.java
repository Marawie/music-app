package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.repository.AuthorRepository;
import music.musicapp.service.interfaceService.AuthorService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl {
    private final AuthorRepository authorRepository;
    private final AuthorService authorService;
}
