package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
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

    @PostMapping("token")
    public void getSpotifyAccessToken() {
        var accessToken = spotifyService.getAccessToken();
    }

    @GetMapping("get/artist")
    public void getArtist(){
        var artist = spotifyService.getArtist();
        System.out.println(artist);
    }

}
