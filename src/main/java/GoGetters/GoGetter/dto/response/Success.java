package GoGetters.GoGetter.dto.response;

import lombok.Data;

@Data
public class Success<T> extends Result{

    private String status;
    private T data;

    public Success(T data){
        this.data=data;
        this.status="success";
    }
}
