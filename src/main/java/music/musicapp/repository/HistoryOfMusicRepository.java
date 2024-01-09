package music.musicapp.repository;

import music.musicapp.model.UserMusicHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryOfMusicRepository extends JpaRepository<UserMusicHistory, Long> {
}