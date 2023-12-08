package music.musicapp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ExceptionResponse> restExceptionHandler(RestException restException) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        restException.getExceptionEnum().getHttpStatus().value(),
                        restException.getMessage()),
                        restException.getExceptionEnum().getHttpStatus());
    }
}