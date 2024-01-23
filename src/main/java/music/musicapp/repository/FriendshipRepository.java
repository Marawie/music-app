package music.musicapp.repository;

import music.musicapp.model.user.Friendship;
import music.musicapp.model.user.FriendshipRequestState;
import music.musicapp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Friendship findByUserAndUserFriends(User user, User userFriend);
    Friendship findByUserFriendsAndUser(User user, User userFriend);
    @Query("SELECT f FROM Friendship f WHERE (f.user = :user OR f.userFriends = :user) AND f.friendshipRequestState = :state")
    Set<Friendship> findAllByUserAndFriendshipRequestState(@Param("user") User user, @Param("state") FriendshipRequestState state);
}