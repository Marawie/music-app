package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.repository.FriendshipRepository;
import music.musicapp.service.interfaceService.FriendshipService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendshipImpl implements FriendshipService {
    private final FriendshipRepository friendshipRepository;

}
