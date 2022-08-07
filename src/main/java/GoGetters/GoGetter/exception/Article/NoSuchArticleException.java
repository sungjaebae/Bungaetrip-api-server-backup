package GoGetters.GoGetter.exception.Article;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchArticleException extends RuntimeException {
    public NoSuchArticleException(String message) {
        super(message);
    }
}
