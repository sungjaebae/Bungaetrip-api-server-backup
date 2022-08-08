package GoGetters.GoGetter.dto.report;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleReportRequest{
    private Long articleId;
    private String reportContent;
}
