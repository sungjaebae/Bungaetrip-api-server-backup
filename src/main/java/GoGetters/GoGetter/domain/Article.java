package GoGetters.GoGetter.domain;

import GoGetters.GoGetter.dto.ArticleFixes;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    private String departure;

    private String destination;

    private LocalDate date;

    private LocalTime time;

    private Integer currentParticipants;

    private Integer totalParticipants;

    private LocalDateTime createdTime;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private ArticleStatus status;
    public Article(User user,String departure,String destination,LocalDate date,LocalTime time,
                   Integer currentParticipants,Integer totalParticipants,
                   String title,String content){
        this(departure,destination,date,time,currentParticipants,totalParticipants, title,content);
        this.setWriter(user);
    }
    public Article(String departure,String destination,LocalDate date,LocalTime time,
                   Integer currentParticipants,Integer totalParticipants,
            String title,String content){
        this.departure=departure;
        this.destination=destination;
        this.date=date;
        this.time=time;
        this.currentParticipants=currentParticipants;
        this.totalParticipants=totalParticipants;
        this.title=title;
        this.content=content;

        this.createdTime=LocalDateTime.now();
        this.status=ArticleStatus.CREATE;
    }

    public void changeArticleStatus(ArticleStatus status){
        this.status=status;
    }

    public void modifyArticle(ArticleFixes fixes){
        this.departure=fixes.getDeparture();
        this.destination=fixes.getDestination();
        this.date=fixes.getDate();
        this.time=fixes.getTime();
        this.currentParticipants= fixes.getCurrentParticipants();
        this.totalParticipants= fixes.getTotalParticipants();;
        this.title=fixes.getTitle();
        this.content= fixes.getContent();
    }


    //연관관계 메서드
    public void setWriter(User user){
        this.writer=user;
    }
}
