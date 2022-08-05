package GoGetters.GoGetter.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchMemberException extends RuntimeException {
    public NoSuchMemberException(String message) {
        super(message);
    }
}
