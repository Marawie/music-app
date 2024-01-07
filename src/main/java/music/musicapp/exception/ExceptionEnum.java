package music.musicapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    SPOTIFY_AUTHORIZATION_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Wrong credentials for spotify auth"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User is not in our database, or wrong input"),
    PASSWORD_NOT_MATCHES(HttpStatus.BAD_REQUEST, "User has to type correct old password to change it"),
    PASSWORD_ARE_NOT_THE_SAME(HttpStatus.BAD_REQUEST, "User has to type two the same password to change it")

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
