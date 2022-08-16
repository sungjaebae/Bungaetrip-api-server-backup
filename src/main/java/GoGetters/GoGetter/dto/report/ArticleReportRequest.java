package GoGetters.GoGetter.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleReportRequest{
    @Schema(description = "게시글 번호")
    private Long articleId;

    @Schema(description = "신고 사유")
    private String reportContent;
}
