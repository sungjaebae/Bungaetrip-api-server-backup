package GoGetters.GoGetter.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Success<T> extends Result{

    @ApiModelProperty(example = "응답 상태")
    private String status;
    @ApiModelProperty(example = "응답 데이터")
    private T data;

    public Success(T data){
        this.data=data;
        this.status="success";
    }
}
