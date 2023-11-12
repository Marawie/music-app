package investment.portfolio.investment.portfolio.repository;

import investment.portfolio.investment.portfolio.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MusicRepository extends JpaRepository<Music, Long> {
}
