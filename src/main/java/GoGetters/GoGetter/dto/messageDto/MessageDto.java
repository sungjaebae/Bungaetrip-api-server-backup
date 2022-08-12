package GoGetters.GoGetter.dto.messageDto;

import GoGetters.GoGetter.domain.Message;
import GoGetters.GoGetter.dto.memberDto.MemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageDto {
    private Long messageId;
    private MemberInfoDto sender;
    private MemberInfoDto receiver;
    private String content;
    private LocalDateTime createdAt;

    public MessageDto(Message message) {
        this.messageId=message.getId();
        this.sender = new MemberInfoDto(message.getSender().getMember());
        this.receiver = new MemberInfoDto(message.getReceiver().getMember());
        this.content=message.getContent();
        this.createdAt=message.getCreatedAt();
    }
}
