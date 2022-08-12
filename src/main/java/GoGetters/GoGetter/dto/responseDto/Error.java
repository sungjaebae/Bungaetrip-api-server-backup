package GoGetters.GoGetter.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
