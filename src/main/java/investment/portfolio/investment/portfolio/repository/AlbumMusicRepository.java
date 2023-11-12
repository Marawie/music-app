package investment.portfolio.investment.portfolio.repository;

import investment.portfolio.investment.portfolio.model.AlbumMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumMusicRepository extends JpaRepository<AlbumMusic, Long> {
}
