package GoGetters.GoGetter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageRequest {
    private String content;

    private Long sender_id;

    private Long receiver_id;
}
