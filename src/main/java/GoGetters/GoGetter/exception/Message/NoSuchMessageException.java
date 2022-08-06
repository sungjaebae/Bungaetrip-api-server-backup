package GoGetters.GoGetter.exception.Message;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchMessageException extends RuntimeException{
    public NoSuchMessageException(String message){
        super(message);
    }
}
