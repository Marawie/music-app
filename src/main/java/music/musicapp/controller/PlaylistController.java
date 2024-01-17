package music.musicapp.controller;


import lombok.RequiredArgsConstructor;
import music.musicapp.dto.PlaylistDto;
import music.musicapp.model.Playlist;
import music.musicapp.service.interfaceService.PlaylistService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/panel/")
@PreAuthorize("hasRole('USER')")
public class PlaylistController {
    private final PlaylistService playlistService;

    @PostMapping("create/new/playlist")
    @PreAuthorize("hasAuthority('user:create')")
    public PlaylistDto addPlaylist(@RequestParam Playlist playlist, Principal principal) {
        return playlistService.addPlaylist(principal, playlist);
    }

    @DeleteMapping("remove/playlist/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public PlaylistDto removePlaylist(@PathVariable Long id, Principal principal) {
        return playlistService.removePlaylist(principal, id);
    }

    @PutMapping("update/playlist/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public PlaylistDto editPlaylist(@PathVariable Long id, @RequestParam String nameOfPlaylist) {
        return playlistService.updatePlaylistName(id, nameOfPlaylist);
    }

    @PatchMapping("update/playlistPrivation/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public PlaylistDto changePrivacyPlaylist(@PathVariable Long playlistId, boolean isPrivate) {
        return playlistService.changePrivacyStatus(playlistId, isPrivate);
    }
}
