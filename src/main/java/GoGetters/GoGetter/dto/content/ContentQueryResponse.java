package GoGetters.GoGetter.dto.content;

import GoGetters.GoGetter.domain.content.ContentType;
import GoGetters.GoGetter.dto.image.ContentImageDto;
import lombok.Data;

import java.util.List;

@Data
public class ContentQueryResponse {
    private Long contentId;
    private String title;

    private String subtitle;


    private String contentType;

    private Double rating;

    private Integer blogReview;

    private Integer visitorReview;


    private Double distance;

    private List<ContentImageDto> imageList;


    public ContentQueryResponse(Long contentId, String title, String subtitle,
                                ContentType contentType, Double rating,
                                Integer blogReview, Integer visitorReview
            ,double distance
    ) {
        this.contentId=contentId;
        this.title = title;
        this.subtitle=subtitle;
        this.contentType=contentType.toString();
        this.rating=rating;
        this.blogReview = blogReview;
        this.visitorReview=visitorReview;
        this.distance=distance;

    }
}
