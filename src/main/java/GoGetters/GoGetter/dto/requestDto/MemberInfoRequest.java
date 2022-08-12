package GoGetters.GoGetter.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoRequest {
    private Long memberId;
    private String nickname;
    private Integer age;
    private String gender;
    private String description;

}
