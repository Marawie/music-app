package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.FriendshipDto;
import music.musicapp.dto.UserDto;
import music.musicapp.service.interfaceService.FriendshipService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
@RequestMapping("user/friends/")
public class FriendshipController {
    private final FriendshipService friendshipService;


    @GetMapping("all")
    public Set<UserDto> ListOfFriends(Principal principal) {
        return friendshipService.getAllFriendship(principal);

    }

    @PostMapping("add/request/{friendId}")
    @PreAuthorize("hasAuthority('user:create')")
    public FriendshipDto sendRequestToFriendToGetFriendship(Principal principal, @PathVariable Long friendId) {
        return friendshipService.addUserToFriend(principal, friendId);
    }

    @PostMapping("accept/request/{friendId}")
    @PreAuthorize("hasAuthority('user:create')")
    public UserDto acceptRequestFromFriend(Principal principal, @PathVariable Long friendId) {
        return friendshipService.acceptFriendshipRequest(principal, friendId);
    }

    @PostMapping("reject/request/{friendId}")
    @PreAuthorize("hasAuthority('user:create')")
    public UserDto rejectRequestFromFriend(Principal principal, @PathVariable Long friendId) {
        return friendshipService.rejectFriendshipRequest(principal, friendId);
    }

    @PostMapping("withdrawn/request")
    @PreAuthorize("hasAuthority('user:create')")
    public UserDto withdrawnYourRequest(Principal principal, @PathVariable Long friendId) {
        return friendshipService.withdrawnRequestByUser(principal, friendId);
    }
}
