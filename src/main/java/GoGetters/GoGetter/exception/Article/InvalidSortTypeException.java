package GoGetters.GoGetter.exception.Article;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidSortTypeException extends RuntimeException {
    public InvalidSortTypeException(String message) {
        super(message);
    }
}
