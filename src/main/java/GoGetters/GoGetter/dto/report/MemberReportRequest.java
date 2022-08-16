package GoGetters.GoGetter.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberReportRequest{
    @Schema(description = "회원 번호")
    private Long memberId;

    @Schema(description = "신고 사유")
    private String reportContent;
}