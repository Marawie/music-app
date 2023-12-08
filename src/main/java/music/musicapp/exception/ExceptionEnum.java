package music.musicapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    SPOTIFY_AUTHORIZATION_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Wrong credentials for spotify auth"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User is not in our database, or wrong input")

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
