package music.musicapp.service.interfaceService;

import music.musicapp.model.Music;
import music.musicapp.model.user.User;

import java.util.Optional;

public interface PlaylistService {
    Optional<User> checkUserPlaylistByMusicYouLiked(Music music);
}
