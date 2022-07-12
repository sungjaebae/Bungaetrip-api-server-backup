package GoGetters.GoGetter.dto.RequestDto;

import GoGetters.GoGetter.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private String nickName;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

}
