package music.musicapp.service.interfaceService;

import music.musicapp.model.user.User;

import java.util.Optional;

public interface UserService {
    Optional<User> globalSearch(String username, String music);
    User addUserToFriends(String username);
}
