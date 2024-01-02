package music.musicapp.service.interfaceService;

import music.musicapp.dto.FriendshipDto;
import music.musicapp.dto.UserDto;

import java.security.Principal;

public interface FriendshipService {

    FriendshipDto addUserToFriend(Principal principal, Long friendId);

    UserDto acceptFriendshipRequest(Principal principal, Long friendId);

    UserDto rejectFriendshipRequest(Principal principal, Long friendId);

}
