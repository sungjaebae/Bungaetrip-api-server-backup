package GoGetters.GoGetter.dto.messageDto;

import GoGetters.GoGetter.domain.Message;
import GoGetters.GoGetter.dto.memberDto.MemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SentMessageResponse {
    private Long messageId;
    private MemberInfoDto receiver;
    private String content;
    private LocalDateTime createdAt;

    public SentMessageResponse(Message message) {
        this.messageId=message.getId();
        this.receiver = new MemberInfoDto(message.getReceiver().getMember());
        this.content=message.getContent();
        this.createdAt=message.getCreatedAt();
    }
}
