package GoGetters.GoGetter.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
    @Schema(description = "메세지 보내는 회원 번호")
    private Long senderId;

    @Schema(description = "메세지 받는 회원 번호")
    private Long receiverId;

    @Schema(description = "메세지 내용")
    private String content;


}
