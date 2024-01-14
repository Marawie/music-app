package music.musicapp.service;

import music.musicapp.dto.PlaylistDto;
import music.musicapp.exception.RestException;
import music.musicapp.model.Playlist;
import music.musicapp.repository.PlaylistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class PlaylistServiceImplTest {

    private PlaylistRepository playlistRepository;
    private PlaylistServiceImpl playlistService;

    @BeforeEach
    void setUp() {
        playlistRepository = mock(PlaylistRepository.class);
        playlistService = new PlaylistServiceImpl(playlistRepository);
    }
    @Test
    void changePrivacyStatusShouldUpdatePrivacyStatus() {

        // Given
        Long playlistId = 1L;
        boolean isPrivate = true;

        Playlist existingPlaylist = new Playlist();
        existingPlaylist.setId(playlistId);
        existingPlaylist.setPrivate(!isPrivate);

        // When
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(existingPlaylist));
        when(playlistRepository.save(any(Playlist.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PlaylistDto result = playlistService.changePrivacyStatus(playlistId, isPrivate);

        // Then
        verify(playlistRepository, times(1)).findById(playlistId);
        verify(playlistRepository, times(1)).save(any(Playlist.class));
        assertAll(() -> {
            assert result != null;
            assert result.getId().equals(playlistId);
            assert result.isPrivate() == isPrivate;
        });
    }

    @Test
    void changePrivacyStatusShouldThrowExceptionIfPlaylistNotFound() {

        // Given
        Long playlistId = 1L;
        boolean isPrivate = true;

        //When
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.empty());

        //Then
        assertThrows(RestException.class, () -> playlistService.changePrivacyStatus(playlistId, isPrivate));
        verify(playlistRepository, times(1)).findById(playlistId);
        verify(playlistRepository, never()).save(any(Playlist.class));
    }
}