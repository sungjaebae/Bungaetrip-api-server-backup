package GoGetters.GoGetter.dto.ResponseDto;

import GoGetters.GoGetter.domain.User;
import lombok.Data;

@Data
public class UserResponse {
    private String email;
    private String nickname;
    private String uid;

    public UserResponse(User user) {
        this.uid = user.getUid();
        this.email = user.getEmail();
        this.nickname = user.getNickName();
    }
}
