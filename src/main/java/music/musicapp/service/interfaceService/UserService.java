package music.musicapp.service.interfaceService;

import music.musicapp.model.user.User;

import java.util.List;

public interface UserService {

    List<Object> globalSearch(String query);

    User addUserToFriends(String username, Long currentUserId);
}
