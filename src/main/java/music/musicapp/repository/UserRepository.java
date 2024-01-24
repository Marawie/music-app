package music.musicapp.repository;

import music.musicapp.model.user.ConfirmationState;
import music.musicapp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByConfirmation_ConfirmationState(ConfirmationState confirmationState);

    @Query("SELECT u FROM User u WHERE u.confirmation.token = :confirmationToken")
    Optional<User> findByConfirmationToken(String confirmationToken);
}