package GoGetters.GoGetter.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberRequest {
    @Schema(description = "회원 번호")
    private Long memberId;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "나이")
    private Integer age;

    @Schema(description = "성별")
    private String gender;

    @Schema(description = "자기 소개")
    private String description;

}
