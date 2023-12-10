package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.user.Friendship;
import music.musicapp.model.user.User;
import music.musicapp.repository.*;
import music.musicapp.service.interfaceService.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public User addUserToFriends(String username, Long currentUserId) {
        final User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));
        System.out.println(currentUser);
        final User friend = userRepository.findByUsername(username)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));
        System.out.println(friend);
        Friendship friendship = new Friendship();
        friendship.setUser(currentUser);
        friendship.setFriend(friend);
        System.out.println(friendship);
        currentUser.getFriendships().add(friendship);

        userRepository.save(currentUser);
        return currentUser;
    }
}

