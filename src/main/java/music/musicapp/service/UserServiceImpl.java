package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.ChangePasswordRequest;
import music.musicapp.dto.PlaylistDto;
import music.musicapp.dto.UserDto;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.Playlist;
import music.musicapp.model.user.User;
import music.musicapp.repository.*;
import music.musicapp.service.interfaceService.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final GenreRepository genreRepository;
    private final MusicRepository musicRepository;
    private final PodcastRepository podcastRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;


    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RestException(ExceptionEnum.PASSWORD_NOT_MATCHES);
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new RestException(ExceptionEnum.PASSWORD_ARE_NOT_THE_SAME);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDto addPlaylist(Principal principal, Playlist playlist) {
        final ModelMapper mapper = new ModelMapper();

        final User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        user.getPlaylists().add(playlist);
        userRepository.save(user);

        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto removePlaylist(Principal principal, Long playlistId) {
        final ModelMapper mapper = new ModelMapper();

        final User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        final Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RestException(ExceptionEnum.PLAYLIST_NOT_FOUND));

        user.getPlaylists().remove(playlist);
        userRepository.save(user);

        return mapper.map(user, UserDto.class);
    }

    @Override
    public PlaylistDto updatePlaylistName(Principal principal, Long playlistId, String nameOfMusic) {
        final ModelMapper mapper = new ModelMapper();

       userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        final Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RestException(ExceptionEnum.PLAYLIST_NOT_FOUND));

        playlist.setName(nameOfMusic);
        Playlist changedPlaylistName = playlistRepository.save(playlist);


        return mapper.map(changedPlaylistName, PlaylistDto.class);
    }
}