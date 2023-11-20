package music.musicapp.service.interfaceService;

import music.musicapp.dto.RequestAuthor;
import music.musicapp.model.Author;

import java.util.Optional;

public interface AuthorService {
    Optional<Author> addAuthorOptionalToDatabase(RequestAuthor author);

    Optional<Author> removeAuthorByName(String nameOfArtist);

}
