package GoGetters.GoGetter.dto.MessageDto;

import GoGetters.GoGetter.domain.Message;
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
    private MemberReturnDto receiver;
    private String content;
    private LocalDateTime createdAt;

    public MessageDto(Message message) {
        this.messageId=message.getId();
        this.sender = new MemberReturnDto(message.getSender().getMember());
        this.receiver = new MemberReturnDto(message.getReceiver().getMember());
        this.content=message.getContent();
        this.createdAt=message.getCreatedAt();
    }
}
