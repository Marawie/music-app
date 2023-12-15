package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.FriendshipDto;
import music.musicapp.dto.UserDto;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.user.Friendship;
import music.musicapp.model.user.User;
import music.musicapp.repository.*;
import music.musicapp.service.interfaceService.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final MusicRepository musicRepository;
    private final PodcastRepository podcastRepository;
    private final AuthorRepository authorRepository;


    @Override
    public List<Object> globalSearch(String query) {
        final List<Object> results = new ArrayList<>();

        results.addAll(genreRepository.findByNameContaining(query));
        results.addAll(musicRepository.findByTitleContaining(query));
        results.addAll(podcastRepository.findByNameContaining(query));
        results.addAll(authorRepository.findByNameContaining(query));

        return results;
    }

    @Override
    public UserDto addUserToFriends(String username, Long currentUserId) {
        ModelMapper modelMapper = new ModelMapper();
        final User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));
        System.out.println(currentUser);
        final User friend = userRepository.findByUsername(username)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));
        System.out.println(friend);
        FriendshipDto friendshipDto = new FriendshipDto();
        friendshipDto.setUser(modelMapper.map(currentUser, UserDto.class));
        friendshipDto.setFriend(modelMapper.map(friend, UserDto.class));

        System.out.println(friendshipDto);
        Friendship friendship = modelMapper.map(friendshipDto, Friendship.class);
        System.out.println(friendship);
        currentUser.getFriendships().add(friendship);
        System.out.println(currentUser);
        //  userRepository.saveUserFriendship(friendship);
        return modelMapper.map(currentUser, UserDto.class);
    }
}

