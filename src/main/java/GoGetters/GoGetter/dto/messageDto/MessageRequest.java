package GoGetters.GoGetter.dto.messageDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
    private String content;

    private Long senderId;

    private Long receiverId;
}
