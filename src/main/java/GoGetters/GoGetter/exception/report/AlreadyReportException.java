package GoGetters.GoGetter.exception.report;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AlreadyReportException extends RuntimeException {
    public AlreadyReportException(String message) {
        super(message);
    }
}
