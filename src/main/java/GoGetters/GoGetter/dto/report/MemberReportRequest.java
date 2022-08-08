package GoGetters.GoGetter.dto.report;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberReportRequest{
    private Long memberId;
    private String reportContent;
}