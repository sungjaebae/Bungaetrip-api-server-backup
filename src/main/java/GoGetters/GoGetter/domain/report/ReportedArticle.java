package GoGetters.GoGetter.domain.report;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ReportedArticle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_article_id")
    private Long id;

    private Long articleId;
    private String content;

    public ReportedArticle(Long articleId, String content) {
        this.articleId=articleId;
        this.content=content;
    }
}
