package music.musicapp.service.interfaceService;
import music.musicapp.dto.ChangePasswordRequest;
import java.security.Principal;
import java.util.List;

public interface UserService {

    List<Object> globalSearch(String query);

    void changePassword(ChangePasswordRequest request,  Principal connectedUser);

}