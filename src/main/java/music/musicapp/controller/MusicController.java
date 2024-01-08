package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.MusicDto;
import music.musicapp.dto.MusicRequest;
import music.musicapp.service.MusicServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/")
@PreAuthorize("hasRole('USER_PREMIUM')")
public class MusicController {

    private final MusicServiceImpl musicService;

    @PostMapping("add-music")
    @PreAuthorize("hasAuthority('user_premium:create')")
    public MusicDto createNewMusic(@RequestBody MusicRequest musicRequest) throws IOException {
        return musicService.createNewMusic(musicRequest);
    }
}
