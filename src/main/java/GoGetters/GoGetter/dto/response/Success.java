package GoGetters.GoGetter.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Success<T> extends Result{

    private String status;
    private T data;

    public Success(T data){
        this.data=data;
        this.status="success";
    }
}
