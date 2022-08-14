package GoGetters.GoGetter.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberRequest {
    private Long memberId;
    private String nickname;
    private Integer age;
    private String gender;
    private String description;

}
