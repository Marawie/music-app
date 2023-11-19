package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.model.user.User;
import music.musicapp.repository.UserRepository;
import music.musicapp.service.interfaceService.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findUserByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<User> addUserToFriends(String username) {
        return Optional.empty();
    }
}

