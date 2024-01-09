package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.model.Music;
import music.musicapp.model.user.User;
import music.musicapp.repository.PlaylistRepository;
import music.musicapp.service.interfaceService.PlaylistService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;
    @Override
    public Optional<User> checkUserPlaylistByMusicYouLiked(Music music) {
        return Optional.empty();
    }
}