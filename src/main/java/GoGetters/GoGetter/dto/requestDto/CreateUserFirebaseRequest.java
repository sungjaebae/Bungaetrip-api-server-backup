package GoGetters.GoGetter.dto.requestDto;

import GoGetters.GoGetter.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserFirebaseRequest {
    private String nickname;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

}
