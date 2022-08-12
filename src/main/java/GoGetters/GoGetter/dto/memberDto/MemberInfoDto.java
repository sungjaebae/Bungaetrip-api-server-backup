package GoGetters.GoGetter.dto.memberDto;

import GoGetters.GoGetter.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberInfoDto {
    private Long memberId;
    private String email;
    private String nickname;
    private String username;
    private String profileUrl;

    public MemberInfoDto(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.username = member.getUsername();
        this.profileUrl = member.getProfileUrl();
    }

}
