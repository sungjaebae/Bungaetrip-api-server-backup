package GoGetters.GoGetter.dto.MessageDto;

import GoGetters.GoGetter.domain.Message;
import GoGetters.GoGetter.dto.returnDto.MemberReturnDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceivedMessageDto {
    private Long messageId;
    private MemberReturnDto sender;
    private String content;
    private LocalDateTime createdAt;

    public ReceivedMessageDto(Message message) {
        this.messageId=message.getId();
        this.sender = new MemberReturnDto(message.getSender().getMember());
        this.content=message.getContent();
        this.createdAt=message.getCreatedAt();
    }
}
