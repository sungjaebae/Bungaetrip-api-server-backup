package GoGetters.GoGetter.dto.member;

import GoGetters.GoGetter.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberInfoDto {
    @Schema(description = "회원 번호")
    private Long memberId;

    @Schema(description = "회원 아이디")
    private String username;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "회원 닉네임")
    private String nickname;

    @Schema(description = "회원 프로필 사진 url")
    private String profileUrl;

    public MemberInfoDto(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.username = member.getUsername();
        this.profileUrl = member.getProfileUrl();
    }

}
