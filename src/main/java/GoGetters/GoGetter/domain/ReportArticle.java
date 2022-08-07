package GoGetters.GoGetter.domain;

import com.azure.core.annotation.Get;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ReportArticle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_article_id")
    private Long id;

    private Long articleId;
    private String content;

    public ReportArticle(Long articleId, String content) {
        this.articleId=articleId;
        this.content=content;
    }
}
