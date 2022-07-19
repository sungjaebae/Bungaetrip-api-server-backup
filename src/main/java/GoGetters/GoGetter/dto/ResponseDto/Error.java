package GoGetters.GoGetter.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
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
