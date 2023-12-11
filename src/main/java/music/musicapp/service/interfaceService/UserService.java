package music.musicapp.service.interfaceService;

import music.musicapp.dto.UserDto;

import java.util.List;

public interface UserService {

    List<Object> globalSearch(String query);

    UserDto addUserToFriends(String username, Long currentUserId);
}
