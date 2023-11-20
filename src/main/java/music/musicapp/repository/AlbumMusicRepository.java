package music.musicapp.repository;

import music.musicapp.model.AlbumMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumMusicRepository extends JpaRepository<AlbumMusic, Long> {
}
