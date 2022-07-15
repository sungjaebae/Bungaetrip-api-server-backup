package GoGetters.GoGetter.dto.ResponseDto;

import GoGetters.GoGetter.domain.Member;
import lombok.Data;

@Data
public class UserResponse {
    private String email;
    private String nickname;
    private String uid;

    public UserResponse(Member member) {
        this.uid = member.getUid();
        this.email = member.getEmail();
        this.nickname = member.getNickName();
    }
}
