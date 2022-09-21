package GoGetters.GoGetter.domain.content;

import GoGetters.GoGetter.domain.JpaBaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter @NoArgsConstructor
public class Content extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;

    private String title;

    private String content;

    private String thumbnail;

    private Double latitude;
    private Double longitude;

    @Enumerated(value = EnumType.STRING)
    private ContentType contentType;

    private Integer likes;

    private Long review;
    private Long rating;
    private Long kakaoId;
    private String address;

    public Content(String title, String content, Double latitude, Double longitude, Integer likes) {
        this.title=title;
        this.content=content;
        this.latitude=latitude;
        this.longitude=longitude;
        this.likes=likes;
    }
}
