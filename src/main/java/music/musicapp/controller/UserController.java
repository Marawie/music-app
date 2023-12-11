package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.UserDto;
import music.musicapp.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.client.HttpClientErrorException.NotFound;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/panel/")
@PreAuthorize("hasRole('USER')")
public class UserController {
    private final UserServiceImpl userService;


    @GetMapping("/search")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<List<Object>> globalSearch(@RequestParam String query) {
        return new ResponseEntity<>(userService.globalSearch(query), HttpStatus.OK);
    }

    @PostMapping("/add/friend/{currentUserId}")
    @PreAuthorize("hasAuthority('user:create')")
    public ResponseEntity<UserDto> addToFriendList(@RequestParam String username, @PathVariable Long currentUserId) {
        try {
            UserDto updatedUser = userService.addUserToFriends(username, currentUserId);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (NotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
