package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.RequestAuthor;
import music.musicapp.model.Author;
import music.musicapp.repository.AuthorRepository;

import music.musicapp.service.interfaceService.AuthorService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;


    @Override
    public Optional<Author> addAuthorOptionalToDatabase(RequestAuthor author) {
        return Optional.empty();
    }

    @Override
    public Optional<Author> removeAuthorByName(String nameOfArtist) {
        return Optional.empty();
    }
}
