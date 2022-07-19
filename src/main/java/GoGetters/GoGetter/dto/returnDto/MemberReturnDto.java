package GoGetters.GoGetter.dto.returnDto;

import GoGetters.GoGetter.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberReturnDto {
    private Long memberId;
    private String email;
    private String nickname;
    private String username;

    public MemberReturnDto(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.username = member.getUsername();
    }

}
