package music.musicapp.service.interfaceService;

import music.musicapp.dto.ChangePasswordRequest;
import music.musicapp.dto.PlaylistDto;
import music.musicapp.dto.UserDto;
import music.musicapp.model.Playlist;

import java.security.Principal;

public interface UserService {
    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    UserDto addPlaylist(Principal principal, Playlist playlist);

    UserDto removePlaylist(Principal principal, Long playlistId);

    PlaylistDto updatePlaylistName(Principal principal, Long playlistId, String name);
}