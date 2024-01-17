package music.musicapp.service.interfaceService;

import music.musicapp.dto.PlaylistDto;
import music.musicapp.model.Playlist;

import java.security.Principal;

public interface PlaylistService {

    PlaylistDto changePrivacyStatus(Long playlistId, boolean isPrivate);
    PlaylistDto addPlaylist(Principal principal, Playlist playlist);

    PlaylistDto removePlaylist(Principal principal, Long playlistId);

    PlaylistDto updatePlaylistName(Long playlistId, String name);
}