package music.musicapp.service;

import music.musicapp.dto.PlaylistDto;
import music.musicapp.exception.RestException;
import music.musicapp.model.Music;
import music.musicapp.model.Playlist;
import music.musicapp.model.user.User;
import music.musicapp.repository.PlaylistRepository;
import music.musicapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MusicServiceImplTest {
    private UserRepository userRepository;
    private PlaylistRepository playlistRepository;
    private MusicServiceImpl playlistService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        playlistRepository = mock(PlaylistRepository.class);
        playlistService = new MusicServiceImpl(userRepository, playlistRepository);
    }

    @Test
    void testAddMusicToPlaylist() {
        //Given
        Principal principal = mock(Principal.class);
        Music music = new Music();
        Long playlistId = 1L;
        User user = new User();
        Playlist playlist = new Playlist();
        playlist.setPrivate(true);

        //When

        when(userRepository.findByEmail(principal.getName())).thenReturn(Optional.of(user));
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(playlist));

        //Then
        PlaylistDto result = playlistService.addMusicToPlaylist(principal, music, playlistId);

        verify(userRepository).findByEmail(principal.getName());
        verify(playlistRepository).findById(playlistId);
        verify(playlistRepository).save(playlist);
        assert result.isPrivate();
    }

    @Test
    void testAddMusicToPlaylistUserNotFound() {
        // Given
        Principal principal = mock(Principal.class);
        Music music = new Music();
        Long playlistId = 1L;

        //When
        when(userRepository.findByEmail(principal.getName())).thenReturn(Optional.empty());

        //Then
        assertThrows(RestException.class, () ->
                playlistService.addMusicToPlaylist(principal, music, playlistId));

        verifyNoInteractions(playlistRepository);
        verify(playlistRepository, never()).save(any());
    }

    @Test
    void testAddMusicToPlaylistPlaylistNotFound() {
        //Given
        Music music = new Music();
        Principal principal = mock(Principal.class);
        Long playlistId = 1L;
        User user = new User();

        //When
        when(userRepository.findByEmail(principal.getName())).thenReturn(Optional.of(user));
        when(playlistRepository.findById(any())).thenReturn(Optional.empty());

        //Then
        assertThrows(RestException.class, () ->
                playlistService.addMusicToPlaylist(principal, music, playlistId));

        verify(playlistRepository, never()).save(any());
    }
}
