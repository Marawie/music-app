package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.FriendshipDto;
import music.musicapp.dto.UserDto;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.user.Friendship;
import music.musicapp.model.user.FriendshipRequestState;
import music.musicapp.model.user.User;
import music.musicapp.repository.FriendshipRepository;
import music.musicapp.repository.UserRepository;
import music.musicapp.service.interfaceService.FriendshipService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class FriendshipServiceImpl implements FriendshipService {
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    @Override
    public FriendshipDto addUserToFriend(Principal principal, Long friendId) {

        final ModelMapper modelMapper = new ModelMapper();

        final User loggedUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        final User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));
        final Friendship friendship = new Friendship(loggedUser, friend, FriendshipRequestState.WAITING_FOR_RESPONSE);
        final Friendship friendshipForFriend = new Friendship(friend, loggedUser, FriendshipRequestState.ACCEPTATION_REQUIRED);
        friendshipRepository.saveAll(List.of(friendship, friendshipForFriend));

        return modelMapper.map(friendship, FriendshipDto.class);
    }

    @Override
    public UserDto acceptFriendshipRequest(Principal principal, Long friendId) {
        final ModelMapper modelMapper = new ModelMapper();

        final User loggedUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        final User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        Friendship friendshipForUser = friendshipRepository.findByUserAndUserFriends(loggedUser, friend);
        Friendship friendshipSForUserFriend = friendshipRepository.findByUserFriendsAndUser(friend, loggedUser);

        friendshipForUser.setFriendshipRequestState(FriendshipRequestState.ACCEPTED);
        friendshipSForUserFriend.setFriendshipRequestState(FriendshipRequestState.ACCEPTED);

        return modelMapper.map(friendshipSForUserFriend.getUser(), UserDto.class);
    }

    @Override
    public UserDto rejectFriendshipRequest(Principal principal, Long friendId) {
        final ModelMapper modelMapper = new ModelMapper();

        final User loggedUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        final User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        Friendship friendshipForUser = friendshipRepository.findByUserAndUserFriends(loggedUser, friend);
        Friendship friendshipSForUserFriend = friendshipRepository.findByUserFriendsAndUser(friend, loggedUser);

        friendshipForUser.setFriendshipRequestState(FriendshipRequestState.REJECTED_BY_YOU);
        friendshipSForUserFriend.setFriendshipRequestState(FriendshipRequestState.REJECTED_BY_USER);

        return modelMapper.map(friendshipSForUserFriend.getUser(), UserDto.class);
    }

    @Override
    public UserDto withdrawnRequestByUser(Principal principal, Long friendId) {
        final ModelMapper modelMapper = new ModelMapper();

        final User loggedUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        final User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        Friendship friendshipForUser = friendshipRepository.findByUserAndUserFriends(loggedUser, friend);
        Friendship friendshipForUserFriend = friendshipRepository.findByUserFriendsAndUser(friend, loggedUser);

        if (friendshipForUserFriend.getFriendshipRequestState() != FriendshipRequestState.ACCEPTED
            && friendshipForUser.getFriendshipRequestState() != FriendshipRequestState.ACCEPTED) {

            friendshipForUser.setFriendshipRequestState(FriendshipRequestState.WITHDRAWN);
            friendshipForUserFriend.setFriendshipRequestState(FriendshipRequestState.WITHDRAWN);
        }

        return modelMapper.map(friendshipForUserFriend.getUser(), UserDto.class);
    }

    @Override
    public Set<UserDto> getAllFriendship(Principal principal) {
        final ModelMapper modelMapper = new ModelMapper();

        final User loggedUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        Set<Friendship> friends = loggedUser.getFriends();

        var users = friends.stream()
                .map(Friendship::getFriend)
                .collect(Collectors.toSet());

        return Collections.singleton(modelMapper.map(users, UserDto.class));
    }
}