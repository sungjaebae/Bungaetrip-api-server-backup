package GoGetters.GoGetter.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CanMemberGenerate {
    private String userInput;
    private Boolean IsAvailable;
}