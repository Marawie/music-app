package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.ChangePasswordRequest;
import music.musicapp.dto.PlaylistDto;
import music.musicapp.dto.UserDto;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.Playlist;
import music.musicapp.model.user.User;
import music.musicapp.repository.*;
import music.musicapp.service.interfaceService.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final GenreRepository genreRepository;
    private final MusicRepository musicRepository;
    private final PodcastRepository podcastRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;

    //Tez zalatwic cały mailing naprzyklad JavaMailSender

    //Activate user (dostajesz token od JWTServisu (activteToken)), dalej generujesz link i wysyłasz mailem
    //Zadbaj o to, zeby user który nie aktywował swojego konta poprzez zalozmy tydzin, byl usuniety z bazy (zrób pole daty wysylki maila)
    //Zadbaj o tym zeby kiedy user nieaktywowany sie logowal, on mial komunikat typu aktywuj konto poprzez mail i mogl proprosic o wysylce maila powtornej

    //Zrobić 2FA możliwość
    //1. Ty musisz zadbać o tym żeby użytkownik wlączył możliwośćl używania 2FA (enable 2FA)
    //2. Ty mu musisz wygenerować QRCODE z trzema danymi: nazwa Aplikacji, googleSecretCode, email.
    //3. GoogleSecretCode generowany za pomocą biblioteka getTOTPCode().
    //4. 10 kodów zapasowych w przypadku utraty do GoogleAuthenticator
    //5. Musisz to zapisać w bazie wraz z kodami zapasowymi (każdy kod jednorazowy)
    //6. On musi zaakceptować że wszystko zapisał i sparował urzadzenie z qrcode.
    //7. Jak on to zrobi, to musi sie wlaczyc 2FA.
    //8. Kiedy on loguje, ty sprawdzasz, ze 2FA jest wlaczone, i jesli tak, to wysylasz response z wymaganiem codu 2fa od Authentikator
    //9. Ze ten endpoint z podaniem kodu og autentykatora nie ma byc ogolnie dostepny
    //10. Musi byc utworzonu temporary token ktory jest przydatny tylko pod endpoint z podaniem kodu zapsawego lud kodu z authentykatora
    //11. Jesli podal dobry kod, to zwrocic accessTokena

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = userRepository.findByEmail(connectedUser.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RestException(ExceptionEnum.PASSWORD_NOT_MATCHES);
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new RestException(ExceptionEnum.PASSWORD_ARE_NOT_THE_SAME);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    //Wsyztko do playlist service
    @Override
    public UserDto addPlaylist(Principal principal, Playlist playlist) {
        final ModelMapper mapper = new ModelMapper();

        final User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        user.getPlaylists().add(playlist);
        userRepository.save(user);

        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto removePlaylist(Principal principal, Long playlistId) {
        final ModelMapper mapper = new ModelMapper();

        final User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        final Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RestException(ExceptionEnum.PLAYLIST_NOT_FOUND));

        user.getPlaylists().remove(playlist);
        userRepository.save(user);

        return mapper.map(user, UserDto.class);
    }

    //Zastanów czy playlist publiczny ma być możliwy do edycji z poziomu wszystkich userów, ablo tylko owner ma do tego dostęp
    @Override
    public PlaylistDto updatePlaylistName(Principal principal, Long playlistId, String nameOfMusic) {
        final ModelMapper mapper = new ModelMapper();

       userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        final Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RestException(ExceptionEnum.PLAYLIST_NOT_FOUND));

        playlist.setName(nameOfMusic);
        Playlist changedPlaylistName = playlistRepository.save(playlist);


        return mapper.map(changedPlaylistName, PlaylistDto.class);
    }
}