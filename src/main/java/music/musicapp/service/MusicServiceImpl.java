package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.PlaylistDto;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.Music;
import music.musicapp.model.Playlist;
import music.musicapp.repository.MusicRepository;
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
    private final MusicRepository musicRepository;


    //TODO: tej metodzie trzeba sie przyjerzec
    @Override
    public PlaylistDto addMusicToPlaylist(Principal principal, Music music, Long id) {
        final ModelMapper modelMapper = new ModelMapper();

        userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        final Playlist playlist = playlistRepository.
                findById(id).orElseThrow(
                        () -> new RestException(ExceptionEnum.PLAYLIST_NOT_FOUND));

        playlist.getMusics().add(music);
        playlistRepository.save(playlist);

        return modelMapper.map(playlist, PlaylistDto.class);
    }
}