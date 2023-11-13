package music.musicapp.repository;

import music.musicapp.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MusicRepository extends JpaRepository<Music, Long> {
}
