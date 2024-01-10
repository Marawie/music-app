package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.service.MusicLabelImpl;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("user-label/")
@PreAuthorize("hasRole('USER_PREMIUM')")
public class MusicLabelController {

    private final MusicLabelImpl musicLabel;

    @PostMapping("upload-music")
    @PreAuthorize("hasAuthority('user_premium:create')")
    public Resource uploadMusic(@RequestParam MultipartFile file, @RequestParam String genre, @RequestParam String textOfMusic) throws IOException {
        return musicLabel.uploadMusic(file, genre, textOfMusic);
    }

    @GetMapping(value = "/download-music/{id}", produces = MediaType.ALL_VALUE)
    @PreAuthorize("hasAuthority('user_premium:read')")
    public Resource downloadImage(@PathVariable Long id) {
        return musicLabel.downloadMusic(id);
    }
}