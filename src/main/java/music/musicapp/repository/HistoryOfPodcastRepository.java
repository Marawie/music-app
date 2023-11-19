package music.musicapp.repository;

import music.musicapp.model.UserPodcastHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface HistoryOfPodcastRepository extends JpaRepository<UserPodcastHistory, Long> {
}
