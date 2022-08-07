package GoGetters.GoGetter.exception.Member;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberAlreadyExistException extends RuntimeException{
    public MemberAlreadyExistException(String message) {
        super(message);
    }
}
