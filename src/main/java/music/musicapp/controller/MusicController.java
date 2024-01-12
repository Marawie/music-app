package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.PlaylistDto;
import music.musicapp.model.Music;
import music.musicapp.service.interfaceService.MusicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/")
@PreAuthorize("hasRole('USER_PREMIUM')")
public class MusicController {
    private final MusicService musicService;

    @PostMapping("add-music-to-playlist")
    @PreAuthorize("hasAuthority('user_premium:create')")
    public PlaylistDto addMusicToPlaylist(Principal principal, @RequestParam Music music, @PathVariable Long id) {
        return musicService.addMusicToPlaylist(principal,music, id);
    }
}
