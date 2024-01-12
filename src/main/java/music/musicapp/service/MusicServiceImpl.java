package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.PlaylistDto;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.Music;
import music.musicapp.model.Playlist;
import music.musicapp.model.user.User;
import music.musicapp.repository.PlaylistRepository;
import music.musicapp.repository.UserRepository;
import music.musicapp.service.interfaceService.MusicService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;

    @Override
    public PlaylistDto addMusicToPlaylist(Principal principal, Music music, Long id) {
        final ModelMapper modelMapper = new ModelMapper();

       userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        final Playlist playlist = playlistRepository.
                findById(id).orElseThrow(
                        () -> new RestException(ExceptionEnum.PLAYLIST_NOT_FOUND));

        var added = playlist.getMusics().add(music);
        return modelMapper.map(added, PlaylistDto.class);

    }


}