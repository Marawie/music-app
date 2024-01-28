package music.musicapp.service;

import music.musicapp.dto.FriendshipDto;
import music.musicapp.dto.UserDto;
import music.musicapp.model.user.Friendship;
import music.musicapp.model.user.FriendshipRequestState;
import music.musicapp.model.user.User;
import music.musicapp.repository.FriendshipRepository;
import music.musicapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        assertEquals(FriendshipRequestState.WAITING_FOR_RESPONSE, result.getState());
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
        assertEquals(FriendshipRequestState.ACCEPTED, friendship.getFriendshipRequestState());
        assertEquals(friendUser.getId(), result.getId());
    }

    @Test
    void testRejectFriendshipRequest() {
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

        UserDto result = friendshipService.rejectFriendshipRequest(principal, friendId);

        // Then
        // Assertions.assertEquals(FriendshipRequestState.REJECTED_BY_YOU, friendship.getFriendshipRequestState());
        assertEquals(FriendshipRequestState.REJECTED_BY_USER, friendship.getFriendshipRequestState());
        assertEquals(friendUser.getId(), result.getId());
    }

    @Test
    void testWithdrawnRequestByUser() {
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

        UserDto result = friendshipService.withdrawnRequestByUser(principal, friendId);

        // Then
        assertEquals(FriendshipRequestState.WITHDRAWN, friendship.getFriendshipRequestState());
        assertEquals(FriendshipRequestState.WITHDRAWN, friendship.getFriendshipRequestState());
        assertEquals(friendUser.getId(), result.getId());
    }


    @Test
    void testGetAllFriendship() {
        // Given
        Principal principal = mock(Principal.class);
        User loggedUser = new User();
        User friendUser1 = new User();
        User friendUser2 = new User();
        loggedUser.setId(1L);
        friendUser1.setId(2L);
        friendUser2.setId(3L);

        Set<Friendship> friendships = new HashSet<>();
        friendships.add(new Friendship(loggedUser, friendUser1, FriendshipRequestState.ACCEPTED));
        friendships.add(new Friendship(loggedUser, friendUser2, FriendshipRequestState.ACCEPTED));

        loggedUser.setFriends(friendships);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(loggedUser));

        when(friendshipRepository.findAllByUserAndFriendshipRequestState(loggedUser, FriendshipRequestState.ACCEPTED))
                .thenReturn(friendships);

        // When
        when(principal.getName()).thenReturn("user@example.com");
        Set<UserDto> result = friendshipService.getAllFriendship(principal);

        // Then
   //TODO cos jest zle w tej metodzie albo tescie sprawdzic pozniej
        assertEquals(2, result);
    }
}