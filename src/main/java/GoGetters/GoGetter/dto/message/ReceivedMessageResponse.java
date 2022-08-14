package GoGetters.GoGetter.dto.message;

import GoGetters.GoGetter.domain.message.Message;
import GoGetters.GoGetter.dto.member.MemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceivedMessageResponse {
    private Long messageId;
    private MemberInfoDto sender;
    private String content;
    private LocalDateTime createdAt;

    public ReceivedMessageResponse(Message message) {
        this.messageId=message.getId();
        this.sender = new MemberInfoDto(message.getSender().getMember());
        this.content=message.getContent();
        this.createdAt=message.getCreatedAt();
    }
}
