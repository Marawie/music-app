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
import java.util.List;

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
        return null;
    }

    @Override
    public UserDto rejectFriendshipRequest(Principal principal, Long friendId) {
        return null;
    }
}
