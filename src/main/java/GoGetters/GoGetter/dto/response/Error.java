package GoGetters.GoGetter.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)

public class Error extends Result{
    private String status;
    private String message;
    private int code;

    public Error(String message, org.springframework.http.HttpStatus code) {
        this.status="error";
        this.message=message;
        this.code=code.value();
    }
}
