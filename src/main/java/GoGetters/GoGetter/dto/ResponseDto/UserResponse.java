package GoGetters.GoGetter.dto.ResponseDto;

import GoGetters.GoGetter.domain.Member;
import lombok.Data;

@Data
public class UserResponse {
    private String email;
    private String nickname;

    public UserResponse(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
    }
}
