package GoGetters.GoGetter.dto.article;

import GoGetters.GoGetter.domain.article.Article;
import GoGetters.GoGetter.dto.member.MemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {
    private Long articleId;
    private String departure;

    private String destination;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime time;

    private Integer currentParticipants;

    private LocalDateTime createdAt;
//    private Integer totalParticipants;
    private String title;

    private String content;

    private MemberInfoDto member;
    public ArticleResponse(Article article){
        this.articleId=article.getId();
        this.departure=article.getDeparture();
        this.destination=article.getDestination();
        this.date=article.getDate();
        this.time=article.getTime();
        this.currentParticipants=article.getCurrentParticipants();
//        this.totalParticipants=article.getTotalParticipants();
        this.title=article.getTitle();
        this.content=article.getContent();
        this.createdAt=article.getCreatedAt();

        this.member = new MemberInfoDto(article.getWriter());
    }

    public ArticleResponse(ArticleRequest articleRequest, LocalDate date, LocalTime time) {
        this.departure=articleRequest.getDeparture();
        this.destination=articleRequest.getDestination();
        this.currentParticipants=articleRequest.getCurrentParticipants();
//        this.totalParticipants=articleRequest.getTotalParticipants();
        this.title=articleRequest.getTitle();
        this.content=articleRequest.getContent();
        this.date=date;
        this.time=time;
    }

    public ArticleResponse(UpdateArticleRequest request){
        this.departure=request.getDeparture();
        this.destination=request.getDestination();
        this.currentParticipants=request.getCurrentParticipants();
//        this.totalParticipants=articleRequest.getTotalParticipants();
        this.title=request.getTitle();
        this.content=request.getContent();
        this.date=request.getDate();
        this.time=request.getTime();
    }
}
