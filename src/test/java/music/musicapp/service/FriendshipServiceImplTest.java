package music.musicapp.service;

import music.musicapp.dto.FriendshipDto;
import music.musicapp.dto.UserDto;
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
import java.util.Optional;

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

    @Test
    void testAcceptFriendshipRequest() {
        // Given
        Principal principal = mock(Principal.class);
        Long friendId = 1L;
        User loggedUser = new User();
        User friendUser = new User();
        Friendship friendship = new Friendship(loggedUser, friendUser, FriendshipRequestState.WAITING_FOR_RESPONSE);

        // When
        when(principal.getName()).thenReturn("user@example.com");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(loggedUser));
        when(userRepository.findById(friendId)).thenReturn(Optional.of(friendUser));
        when(friendshipRepository.findByUserAndUserFriends(loggedUser, friendUser)).thenReturn(friendship);
        when(friendshipRepository.findByUserFriendsAndUser(friendUser, loggedUser)).thenReturn(friendship);

        UserDto result = friendshipService.acceptFriendshipRequest(principal, friendId);

        // Then
        Assertions.assertEquals(FriendshipRequestState.ACCEPTED, friendship.getFriendshipRequestState());
        Assertions.assertEquals(friendUser.getId(), result.getId());
    }
}