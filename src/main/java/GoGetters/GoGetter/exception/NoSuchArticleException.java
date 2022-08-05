package GoGetters.GoGetter.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchArticleException extends RuntimeException {
    public NoSuchArticleException(String message) {
        super(message);
    }
}
