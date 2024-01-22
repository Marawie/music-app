package music.musicapp.repository;

import music.musicapp.model.user.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    @Modifying
    @Query("UPDATE Confirmation c SET c.token = :token WHERE c.id = :confirmationId AND c.token IS NULL")
    void saveByConfirmationToken(String token);
    boolean existsByToken(String token);

    @Query("SELECT c FROM Confirmation c WHERE c.token = :token")
    Optional<Confirmation> findByConfirmationToken(@Param("token") String token);
}
