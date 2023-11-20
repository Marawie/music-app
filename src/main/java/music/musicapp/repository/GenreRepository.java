package music.musicapp.repository;

import music.musicapp.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface GenreRepository extends JpaRepository<Genre, Long> {
}