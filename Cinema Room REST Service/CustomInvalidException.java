package cinema;

import org.springframework.http.HttpStatus;

public class CustomInvalidException extends RuntimeException{
    private final HttpStatus error;
    public CustomInvalidException(String message, HttpStatus error) {
        super(message);
        this.error = error;
    }

    public HttpStatus getError() {
        return error;
    }
}
