package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.PlaylistDto;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.Playlist;
import music.musicapp.repository.PlaylistRepository;
import music.musicapp.repository.UserRepository;
import music.musicapp.service.interfaceService.PlaylistService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;


    @Override
    public PlaylistDto changePrivacyStatus(Long playlistId, boolean isPrivate) {
        ModelMapper modelMapper = new ModelMapper();

        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(
                () -> new RestException(ExceptionEnum.PLAYLIST_NOT_FOUND));

        playlist.setPrivate(isPrivate);
        playlistRepository.save(playlist);

        return modelMapper.map(playlist, PlaylistDto.class);
    }
}