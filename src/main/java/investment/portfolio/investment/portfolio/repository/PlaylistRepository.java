package investment.portfolio.investment.portfolio.repository;

import investment.portfolio.investment.portfolio.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
