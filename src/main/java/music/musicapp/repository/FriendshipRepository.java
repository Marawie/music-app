package music.musicapp.repository;

import music.musicapp.model.user.Friendship;
import music.musicapp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Friendship findByUserAndUserFriends(User user, User userFriend);

    Friendship findByUserFriendsAndUser(User user, User userFriend);

}