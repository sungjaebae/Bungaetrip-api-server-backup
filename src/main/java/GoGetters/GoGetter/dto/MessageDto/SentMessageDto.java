package GoGetters.GoGetter.dto.MessageDto;

import GoGetters.GoGetter.domain.Message;
import GoGetters.GoGetter.dto.returnDto.MemberReturnDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SentMessageDto {
    private Long messageId;
    private MemberReturnDto receiver;
    private String content;
    private LocalDateTime createdAt;

    public SentMessageDto(Message message) {
        this.messageId=message.getId();
        this.receiver = new MemberReturnDto(message.getReceiver().getMember());
        this.content=message.getContent();
        this.createdAt=message.getCreatedAt();
    }
}
