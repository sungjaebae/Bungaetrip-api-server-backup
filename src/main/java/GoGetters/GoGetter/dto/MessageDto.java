package GoGetters.GoGetter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageDto {
    private Long messageId;
    private String senderNickName;
    private String content;
    private LocalDateTime created;

}
