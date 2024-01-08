package music.musicapp.service.interfaceService;

import music.musicapp.dto.MusicDto;
import music.musicapp.dto.MusicRequest;

import java.io.IOException;

public interface MusicService {
    MusicDto createNewMusic(MusicRequest musicRequest) throws IOException;

}