package GoGetters.GoGetter.domain.article;

import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.domain.member.Member;
import GoGetters.GoGetter.util.DateTimeUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter @NoArgsConstructor
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ArticleType articleType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    private String departure;

    //출발지 위도
    private Double departureLongitude;
    //출발지 경도
    private Double departureLatitude;

    private String destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content destinationContent;

    //도착지 위도
    private Double destinationLongitude;
    //도착지 경도
    private Double destinationLatitude;

    private LocalDate date;

    private LocalTime time;

    private Integer currentParticipants;

//    private Integer totalParticipants;

    private LocalDateTime createdAt;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    public Article(Member member,ArticleType articleType, String departure, String destination,Content destinationContent,
                   LocalDate date, LocalTime time,
                   Integer currentParticipants,
                   String title, String content,
                   Double departureLongitude,Double departureLatitude,
                   Double destinationLongitude,Double destinationLatitude
    ){
        this(articleType,departure,destination
                ,date,time,currentParticipants, title,content
                ,departureLongitude,departureLatitude,
                destinationLongitude,destinationLatitude);

        this.setWriter(member);
        this.setContent(destinationContent);
    }
    public Article(ArticleType articleType,
            String departure,String destination
            ,LocalDate date,LocalTime time,
                   Integer currentParticipants,
            String title,String content,
    Double departureLongitude,Double departureLatitude,
                   Double destinationLongitude,Double destinationLatitude){
        this.articleType=articleType;
        this.departure=departure;
        this.destination=destination;
        this.date=date;
        this.time=time;
        this.currentParticipants=currentParticipants;
//        this.totalParticipants=totalParticipants;
        this.title=title;
        this.content=content;

        this.createdAt= DateTimeUtils.nowFromZone();
        this.status=ArticleStatus.CREATE;

        this.departureLongitude=departureLongitude;
        this.departureLatitude=departureLatitude;
        this.destinationLongitude=destinationLongitude;
        this.destinationLatitude=destinationLatitude;
    }

    public void changeArticleStatus(ArticleStatus status){
        this.status=status;
    }

    public void modifyArticle(ArticleType articleType,String departure, String destination,Content destinationContent,
                              LocalDate date, LocalTime time,
                              Integer currentParticipants,
                              String title, String content,
                              Double departureLongitude,Double departureLatitude,
                              Double destinationLongitude,Double destinationLatitude){
        this.departure=departure;
        this.destination=destination;
        this.date=date;
        this.time=time;
        this.currentParticipants= currentParticipants;
        this.title=title;
        this.content= content;

        this.departureLongitude=departureLongitude;
        this.departureLatitude=departureLatitude;
        this.destinationLongitude=destinationLongitude;
        this.destinationLatitude=destinationLatitude;

        this.setContent(destinationContent);

    }


    //연관관계 메서드
    public void setWriter(Member member){

        this.writer= member;
    }

    public void setContent(Content destinationContent) {
        this.destinationContent=destinationContent;
        this.destinationContent.getArticles().add(this);
    }
}
