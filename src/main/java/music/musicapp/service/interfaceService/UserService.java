package music.musicapp.service.interfaceService;

import music.musicapp.model.user.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByUsername(String username);
    Optional<User> addUserToFriends(String username);
}
