package music.musicapp.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import music.musicapp.dto.ChangePasswordRequest;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.model.user.User;
import music.musicapp.repository.UserRepository;
import music.musicapp.service.interfaceService.MailSenderService;
import music.musicapp.service.interfaceService.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import static music.musicapp.exception.ExceptionEnum.CONFIRMATION_FAILED;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;

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

    @Transactional
    public void userAcceptedLink(String token) {

        final User user = userRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        if (!user.isAccountActivated()) {
            user.setAccountActivated(true);
            userRepository.save(user);
        } else
            throw new RestException(CONFIRMATION_FAILED);
    }

    @Override
    @Transactional
    public void userReminderEmail(String token) {

        final User user = userRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        if (!user.isAccountActivated()) {
            try {
                mailSenderService.sendEmailReminder(user.getEmail(), "Music-app");
            } catch (MessagingException e) {
                throw new RestException(ExceptionEnum.YOUR_EMAIL_IS_ACTIVATED);
            }
        }
    }

    @Override
    @Transactional
    public boolean handleConfirmationClick(String token) {

        final User user = userRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new RestException(ExceptionEnum.USER_NOT_FOUND));

        if (!user.isAccountActivated()) {
            user.setAccountActivated(true);
            userRepository.save(user);
            return true;
        } else {
            throw new RestException(ExceptionEnum.YOUR_EMAIL_IS_ACTIVATED);
        }
    }


    @Scheduled(cron = "0 0 1 * * ?") // 1am
    @Transactional
    protected void userLinkExpired() throws MessagingException {

        List<User> unacceptedUsers = userRepository.findByIsAccountActivated(
                false);

        for (User user : unacceptedUsers) {
            LocalDateTime localDateTime = user.getDateThatUserCreateAccount();
            if (localDateTime.plusDays(7).isBefore(LocalDateTime.now()) && !user.isAccountActivated()) {
                userRepository.delete(user);
                mailSenderService.sendConfirmationEmail(user.getEmail(), "Your registration confirmation has expired, please register again!");
            }
        }
    }
}