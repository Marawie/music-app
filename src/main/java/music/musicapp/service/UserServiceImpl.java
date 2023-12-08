package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.user.User;
import music.musicapp.repository.UserRepository;
import music.musicapp.service.interfaceService.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> globalSearch(String username, String music) {
      final List<User> userList = userRepository.findAll();
return null;
//TODO: think about how i should do it
    }

    public User addUserToFriends(String username) {
        final List<User> userList = userRepository.findAll();
        final User userFind = userList.stream()
                .filter(user -> user.getUsername().matches(username))
                .findFirst()
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));
        return userRepository.save(userFind);
    }
}

