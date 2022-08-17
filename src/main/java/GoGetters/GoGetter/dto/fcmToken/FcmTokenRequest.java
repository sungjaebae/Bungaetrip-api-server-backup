package GoGetters.GoGetter.dto.fcmToken;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FcmTokenRequest {
    @Schema(description = "사용자 디바이스 토큰")
    private String fcmToken;
}
