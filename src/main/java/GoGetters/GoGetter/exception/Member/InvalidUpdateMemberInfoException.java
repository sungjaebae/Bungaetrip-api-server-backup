package GoGetters.GoGetter.exception.Member;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidUpdateMemberInfoException extends RuntimeException {
    public InvalidUpdateMemberInfoException(String message) {
        super(message);
    }
}
