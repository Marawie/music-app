package music.musicapp.service.interfaceService;

import music.musicapp.dto.ChangePasswordRequest;
import music.musicapp.dto.UserDto;
import music.musicapp.model.Playlist;

import java.security.Principal;
import java.util.List;

public interface UserService {
    List<Object> globalSearch(String query);
    void changePassword(ChangePasswordRequest request,  Principal connectedUser);

    UserDto addPlaylist(Principal principal, Playlist playlist);
    UserDto removePlaylist(Principal principal, Long playlistId, Long id);
    UserDto updatePlaylistName(Principal principal, Long playlistId);
}