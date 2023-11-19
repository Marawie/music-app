package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.service.SpotifyService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SpotifyController {

    private final SpotifyService spotifyService;





}
