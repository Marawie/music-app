package music.musicapp.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import music.musicapp.service.GenreServiceImpl;
import music.musicapp.service.SpotifyServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class SpotifyController {

    private final SpotifyServiceImpl spotifyService;
    private final GenreServiceImpl genreService;

    @PostMapping("token")
    public void getSpotifyAccessToken() {
        spotifyService.getAccessToken();
    }

    @GetMapping("get/artist")
    public void getArtist() {
        spotifyService.getArtist();
    }
}
