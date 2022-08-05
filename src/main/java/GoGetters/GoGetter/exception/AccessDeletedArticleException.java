package GoGetters.GoGetter.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccessDeletedArticleException extends RuntimeException {
    public AccessDeletedArticleException(String message) {
        super(message);
    }
}
