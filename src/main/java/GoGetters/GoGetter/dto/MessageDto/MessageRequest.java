package GoGetters.GoGetter.dto.MessageDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
    private String content;

    private Long senderId;

    private Long receiverId;
}
