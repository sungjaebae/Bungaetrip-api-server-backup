package GoGetters.GoGetter.dto.message;

import GoGetters.GoGetter.domain.message.Message;
import GoGetters.GoGetter.dto.member.MemberInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageDto {
    @Schema(description = "회원 번호")
    private Long messageId;

    @Schema(description = "보낸 사람")
    private MemberInfoDto sender;

    @Schema(description = "받은 사람")
    private MemberInfoDto receiver;

    @Schema(description = "메세지 내용")
    private String content;

    @Schema(description = "메세지 작성 시간")
    private LocalDateTime createdAt;

    public MessageDto(Message message) {
        this.messageId=message.getId();
        this.sender = new MemberInfoDto(message.getSender().getMember());
        this.receiver = new MemberInfoDto(message.getReceiver().getMember());
        this.content=message.getContent();
        this.createdAt=message.getCreatedAt();
    }
}
