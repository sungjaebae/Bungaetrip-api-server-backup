package GoGetters.GoGetter.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberAlreadyExistException extends RuntimeException{
    public MemberAlreadyExistException(String message) {
        super(message);
    }
}
