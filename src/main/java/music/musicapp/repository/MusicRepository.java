package music.musicapp.repository;

import music.musicapp.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface MusicRepository extends JpaRepository<Music, Long> {
    List<Music> findByTitleContaining(String query);
}
