package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.repository.UserRepository;
import music.musicapp.service.interfaceService.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final UserService userService;

}

