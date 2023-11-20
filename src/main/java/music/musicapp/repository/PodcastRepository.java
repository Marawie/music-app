package music.musicapp.repository;

import music.musicapp.model.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PodcastRepository extends JpaRepository<Podcast, Long> {
}