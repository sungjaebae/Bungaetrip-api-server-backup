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

    private String articleType;
    private String departure;
    private Double departureLongitude;
    private Double departureLatitude;
    private String destination;
    private Double destinationLongitude;
    private Double destinationLatitude;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime time;

    private Integer currentParticipants;

    private LocalDateTime createdAt;
    private String title;

    private String content;


    private MemberInfoDto member;
    public ArticleResponse(Article article){
        this.articleId=article.getId();
        this.articleType=article.getArticleType().toString();
        this.departure=article.getDeparture();
        this.destination=article.getDestination();
        this.date=article.getDate();
        this.time=article.getTime();
        this.currentParticipants=article.getCurrentParticipants();
        this.title=article.getTitle();
        this.content=article.getContent();
        this.createdAt=article.getCreatedAt();

        this.member = new MemberInfoDto(article.getWriter());

        this.departureLongitude = article.getDepartureLongitude();
        this.departureLatitude=article.getDepartureLatitude();
        this.destinationLongitude=article.getDestinationLongitude();
        this.destinationLatitude=article.getDestinationLatitude();
    }

    public ArticleResponse(CreateArticleRequest articleRequest, LocalDate date, LocalTime time) {
        this.departure=articleRequest.getDeparture();
        this.destination=articleRequest.getDestination();
        this.currentParticipants=articleRequest.getCurrentParticipants();
        this.title=articleRequest.getTitle();
        this.content=articleRequest.getContent();
        this.date=date;
        this.time=time;
    }

    public ArticleResponse(UpdateArticleRequest request){
        this.departure=request.getDeparture();
        this.destination=request.getDestination();
        this.currentParticipants=request.getCurrentParticipants();
        this.title=request.getTitle();
        this.content=request.getContent();
        this.date=request.getDate();
        this.time=request.getTime();
    }
}
