package GoGetters.GoGetter.domain.content;

import GoGetters.GoGetter.domain.Image.Image;
import GoGetters.GoGetter.domain.article.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @NoArgsConstructor
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;

    private String title;

    private String subtitle;

    private String content;

    @Enumerated(value = EnumType.STRING)
    private ContentType contentType;

    private Double latitude;
    private Double longitude;
//    private Point point;
    private String address;

    private Double rating;

    private Integer blogReview;

    private Integer visitorReview;

    private String phoneNumber;

    private String naverId;

    private String contentDetailUrl;

    @OneToMany(mappedBy = "content",cascade = CascadeType.ALL)
    private Set<Image> images=new HashSet<>();

    @OneToMany(mappedBy = "destinationContent",cascade = CascadeType.ALL)
    private List<Article> articles = new ArrayList<>();
    public Content(String title,String subtitle, String content,ContentType contentType
            ,String address, Double latitude, Double longitude,Double rating,Integer blogReview,Integer visitorReview,
                   String phoneNumber,String naverId,String contentDetailUrl){
        this.title=title;
        this.subtitle=subtitle;
        this.content=content;
        this.contentType=contentType;
        this.latitude=latitude;
        this.longitude=longitude;
        this.address=address;
        this.rating=rating;
        this.blogReview=blogReview;
        this.visitorReview=visitorReview;
        this.phoneNumber=phoneNumber;
        this.naverId=naverId;
        this.contentDetailUrl=contentDetailUrl;
    }



}
