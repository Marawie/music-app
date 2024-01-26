package music.musicapp.service.interfaceService;

import music.musicapp.dto.ChangePasswordRequest;

import java.security.Principal;

public interface UserService {
    void changePassword(ChangePasswordRequest request, Principal connectedUser);
    void userAcceptedLink(Long id, String token);
}