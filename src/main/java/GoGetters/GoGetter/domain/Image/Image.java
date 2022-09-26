package GoGetters.GoGetter.domain.Image;

import GoGetters.GoGetter.domain.content.Content;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    private void setContent(Content content) {
        this.content=content;
        this.content.getImages().add(this);
    }

}
