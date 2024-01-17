package music.musicapp.service;

import music.musicapp.dto.FriendshipDto;
import music.musicapp.model.user.Friendship;
import music.musicapp.model.user.FriendshipRequestState;
import music.musicapp.model.user.User;
import music.musicapp.repository.FriendshipRepository;
import music.musicapp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.security.Principal;
import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FriendshipRepository friendshipRepository;

    @InjectMocks
    private FriendshipServiceImpl friendshipService;

    @Test
    void testAddUserToFriend() {
        // Given
        Principal principal = mock(Principal.class);
        Long friendId = 1L;
        User loggedUser = new User();
        User friendUser = new User();

        //When
        when(principal.getName()).thenReturn("user@example.com");
        when(userRepository.findByEmail("user@example.com")).thenReturn(java.util.Optional.of(loggedUser));
        when(userRepository.findById(friendId)).thenReturn(java.util.Optional.of(friendUser));

        //Then
        FriendshipDto result = friendshipService.addUserToFriend(principal, friendId);

        Assertions.assertEquals(FriendshipRequestState.WAITING_FOR_RESPONSE, result.getState());
        verify(friendshipRepository, times(1)).saveAll(
                List.of(
                        new Friendship(loggedUser, friendUser, FriendshipRequestState.WAITING_FOR_RESPONSE),
                        new Friendship(friendUser, loggedUser, FriendshipRequestState.ACCEPTATION_REQUIRED)
                )
        );
    }
}