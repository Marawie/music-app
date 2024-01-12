package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.ChangePasswordRequest;
import music.musicapp.dto.PlaylistDto;
import music.musicapp.dto.SearchResultDto;
import music.musicapp.dto.UserDto;
import music.musicapp.model.Playlist;
import music.musicapp.service.UserServiceImpl;
import music.musicapp.service.interfaceService.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/panel/")
@PreAuthorize("hasRole('USER')")
public class UserController {
    private final UserServiceImpl userService;
    private final SearchService searchService;


    @GetMapping("search")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<SearchResultDto> globalSearch(@RequestParam String query) {
        return new ResponseEntity<>(searchService.searchEngine(query), HttpStatus.OK);
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
    public PlaylistDto editPlaylist(@PathVariable Long id, Principal principal, @RequestParam String nameOfPlaylist) {
        return userService.updatePlaylistName(principal, id, nameOfPlaylist);
    }
}