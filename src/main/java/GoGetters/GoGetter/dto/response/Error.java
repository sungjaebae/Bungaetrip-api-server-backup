package GoGetters.GoGetter.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)

public class Error {
    @ApiModelProperty(example = "응답 상태")

    private String status;
    @ApiModelProperty(example = "에러 메세지")

    private String message;
    @ApiModelProperty(example = "상태 코드")
    private int code;

    public Error(String message, org.springframework.http.HttpStatus code) {
        this.status="error";
        this.message=message;
        this.code=code.value();
    }
}
