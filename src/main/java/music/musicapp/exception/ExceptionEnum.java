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
    PASSWORD_ARE_NOT_THE_SAME(HttpStatus.BAD_REQUEST, "User has to type two the same password to change it"),
    FILE_REQUEST_DENIED(HttpStatus.BAD_REQUEST, "You can't upload this file, you have to follow our 'upload music instruction' to do this"),
    FILE_UPLOADED (HttpStatus.CREATED, "You uploaded successfully your music file: "),
    WRONG_EXTENSION_FILE(HttpStatus.BAD_REQUEST, "Your file has to be in MP3, FLAC, WAV"),
    WRONG_MIME_TYPES(HttpStatus.BAD_REQUEST, "These types are allowed: audio/mpeg, audio/wav, audio/flac"),
    FILE_TOO_BIG(HttpStatus.BAD_REQUEST, "Your file is too big, we only accepted 25mb by one file"),
    FILE_READ_ERROR(HttpStatus.CONFLICT, "Error while trying to read file"),
    ARTIST_NOT_FOUND(HttpStatus.NOT_FOUND, "This artist doesn't match with any Artist"),
    GENRES_NOT_FOUND(HttpStatus.NOT_FOUND, "This genre doesn't match with any genres"),
    PLAYLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "Playlist not found!"),
    USER_CONFIRMATION_STATE_IS_NOT_ACCEPTED(HttpStatus.FORBIDDEN , "You have to click link which is generated in your email. You have only 7 days to do it, or account would be deleted"),
    CONFIRMATION_IS_NOT_FOUND(HttpStatus.FORBIDDEN, "Cannot find this type of confirmation in our database")
    ;
    private final HttpStatus httpStatus;
    private final String message;
}