package GoGetters.GoGetter.dto;

import GoGetters.GoGetter.domain.Sender;
import GoGetters.GoGetter.dto.returnDto.MemberReturnDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageDto {
    private Long messageId;
    private MemberReturnDto sender;
    private String content;
    private LocalDateTime created;

}
