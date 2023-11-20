package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.model.Music;
import music.musicapp.model.Playlist;
import music.musicapp.repository.MusicRepository;
import music.musicapp.service.interfaceService.MusicService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService{
    private final MusicRepository musicRepository;


    @Override
    public Optional<Music> addMusicWhichIsLikedToPlaylist(Playlist playlist) {
        return Optional.empty();
    }
}
