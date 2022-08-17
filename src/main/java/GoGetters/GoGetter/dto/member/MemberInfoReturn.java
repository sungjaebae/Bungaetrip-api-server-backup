package GoGetters.GoGetter.dto.member;

import GoGetters.GoGetter.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class MemberInfoReturn{
    @Schema(description = "회원 번호",example = "1")

    private Long memberId;
    @Schema(description = "회원 아이디",example = "1")
    private String username;
    private String email;
    private String nickname;
    private Integer age;

    private String gender;
    private String description;
    private String profileUrl;
    private List<Long> blockedPeople;
    public MemberInfoReturn(Member member) {
        this.memberId=member.getId();
        this.username=member.getUsername();
        this.email=member.getEmail();
        this.nickname=member.getNickname();
        this.age= member.getAge();


        this.gender=null;
        if(member.getGender()!=null)
            this.gender=member.getGender().toString();
        this.description=member.getDescription();

        this.blockedPeople= member.getBlockedPeople().stream()
                .map(blockedPeople -> blockedPeople.getReportedMemberId())
                .collect(Collectors.toList());
        this.profileUrl=member.getProfileUrl();
    }
}