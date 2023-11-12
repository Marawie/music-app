package investment.portfolio.investment.portfolio.repository;

import investment.portfolio.investment.portfolio.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
