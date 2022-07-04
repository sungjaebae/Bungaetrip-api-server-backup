package GoGetters.GoGetter.domain;

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

    private LocalDateTime createdTime;

    private String title;

    private String content;

    Article(String departure,String destination,LocalDate date,LocalTime time, Integer currentParticipants,
            String title,String content){
        this.departure=departure;
        this.destination=destination;
        this.date=date;
        this.time=time;
        this.currentParticipants=currentParticipants;
        this.title=title;
        this.content=content;

        this.createdTime=LocalDateTime.now();
    }
}
