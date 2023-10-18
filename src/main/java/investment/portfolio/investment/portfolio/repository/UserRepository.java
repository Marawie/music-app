package investment.portfolio.investment.portfolio.repository;

import investment.portfolio.investment.portfolio.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<User> findByEmail(String username);
}
