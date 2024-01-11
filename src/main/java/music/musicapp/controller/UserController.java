package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.ChangePasswordRequest;
import music.musicapp.dto.UserDto;
import music.musicapp.model.Playlist;
import music.musicapp.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/panel/")
@PreAuthorize("hasRole('USER')")
public class UserController {
    private final UserServiceImpl userService;


    @GetMapping("search")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<List<Object>> globalSearch(@RequestParam String query) {
        return new ResponseEntity<>(userService.globalSearch(query), HttpStatus.OK);
    }

    @PatchMapping("change/password")
    @PreAuthorize("hasAuthority('user:update')")
    public void changePassword(@RequestBody ChangePasswordRequest request, Principal principal) {
        userService.changePassword(request, principal);
    }

    @PostMapping("create/new/playlist")
    @PreAuthorize("hasAuthority('user:create')")
    public UserDto addPlaylist(@RequestParam Playlist playlist, Principal principal) {
        return userService.addPlaylist(principal, playlist);
    }

    @DeleteMapping("remove/playlist/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public UserDto removePlaylist(@PathVariable Long id, Principal principal) {
        return userService.removePlaylist(principal, id);
    }

    @PutMapping("update/playlist/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public UserDto editPlaylist(@PathVariable Long id, Principal principal, @RequestParam String nameOfPlaylist) {
        return userService.updatePlaylistName(principal, id, nameOfPlaylist);
    }
}