package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.PlaylistDto;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.Playlist;
import music.musicapp.model.user.User;
import music.musicapp.repository.PlaylistRepository;
import music.musicapp.repository.UserRepository;
import music.musicapp.service.interfaceService.PlaylistService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    @Override
    public PlaylistDto changePrivacyStatus(Long playlistId, boolean isPrivate) {
        ModelMapper modelMapper = new ModelMapper();

        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(
                () -> new RestException(ExceptionEnum.PLAYLIST_NOT_FOUND));

        playlist.setPrivate(isPrivate);
        playlistRepository.save(playlist);

        return modelMapper.map(playlist, PlaylistDto.class);
    }

    @Override
    public PlaylistDto addPlaylist(Principal principal, Playlist playlist) {
        final ModelMapper mapper = new ModelMapper();

        final User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        user.getPlaylists().add(playlist);
        userRepository.save(user);

        return mapper.map(user.getPlaylists(), PlaylistDto.class);
    }

    @Override
    public PlaylistDto removePlaylist(Principal principal, Long playlistId) {
        final ModelMapper mapper = new ModelMapper();

        final User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        final Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RestException(ExceptionEnum.PLAYLIST_NOT_FOUND));

        user.getPlaylists().remove(playlist);
        userRepository.save(user);

        return mapper.map(user.getPlaylists(), PlaylistDto.class);
    }

    //Zastanów czy playlist publiczny ma być możliwy do edycji z poziomu wszystkich userów, ablo tylko owner ma do tego dostęp
    @Override
    public PlaylistDto updatePlaylistName(Long playlistId, String nameOfMusic) {
        final ModelMapper mapper = new ModelMapper();


        final Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RestException(ExceptionEnum.PLAYLIST_NOT_FOUND));

        playlist.setName(nameOfMusic);
        Playlist changedPlaylistName = playlistRepository.save(playlist);

        return mapper.map(changedPlaylistName, PlaylistDto.class);
    }
}