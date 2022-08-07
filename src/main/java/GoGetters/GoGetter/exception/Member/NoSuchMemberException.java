package GoGetters.GoGetter.exception.Member;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchMemberException extends RuntimeException {
    public NoSuchMemberException(String message) {
        super(message);
    }
}
