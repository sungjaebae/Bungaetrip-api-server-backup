package GoGetters.GoGetter.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Success<T>{

    @Schema(description = "응답 상태")
    private String status;
    @Schema(description= "응답 데이터")
    private T data;

    public Success(T data){
        this.data=data;
        this.status="success";
    }
}
