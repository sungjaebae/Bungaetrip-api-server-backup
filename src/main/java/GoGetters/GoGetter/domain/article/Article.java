package GoGetters.GoGetter.domain.article;

import GoGetters.GoGetter.domain.member.Member;
import GoGetters.GoGetter.dto.article.UpdateArticleRequest;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    private String departure;

    private String destination;

    private LocalDate date;

    private LocalTime time;

    private Integer currentParticipants;

//    private Integer totalParticipants;

    private LocalDateTime createdAt;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private ArticleStatus status;
    public Article(Member member, String departure, String destination, LocalDate date, LocalTime time,
                   Integer currentParticipants,
//                   Integer totalParticipants,
                   String title, String content){
        this(departure,destination,date,time,currentParticipants, title,content);
        this.setWriter(member);
    }
    public Article(String departure,String destination,LocalDate date,LocalTime time,
                   Integer currentParticipants,
//                   Integer totalParticipants,
            String title,String content){
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
    }

    public void changeArticleStatus(ArticleStatus status){
        this.status=status;
    }

    public void modifyArticle(UpdateArticleRequest fixes){
        this.departure=fixes.getDeparture();
        this.destination=fixes.getDestination();
        this.date=fixes.getDate();
        this.time=fixes.getTime();
        this.currentParticipants= fixes.getCurrentParticipants();
//        this.totalParticipants= fixes.getTotalParticipants();;
        this.title=fixes.getTitle();
        this.content= fixes.getContent();
    }


    //연관관계 메서드
    public void setWriter(Member member){
        this.writer= member;
    }
}
