package GoGetters.GoGetter.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchMessageException extends RuntimeException{
    public NoSuchMessageException(String message){
        super(message);
    }
}
