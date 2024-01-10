package music.musicapp.repository;

import music.musicapp.model.Playlist;
import music.musicapp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);


    User deletePlaylist(Playlist playlist);
}