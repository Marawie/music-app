package music.musicapp.repository;

import music.musicapp.model.AlbumPodcast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumPodcastRepository extends JpaRepository<AlbumPodcast, Long> {
}
