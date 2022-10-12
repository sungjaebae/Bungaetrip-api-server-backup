package GoGetters.GoGetter.dto.content;

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


//    public ContentQueryResponse(Long contentId, String title, String subtitle,
//                                ContentType contentType, Double rating,
//                                Integer blogReview, Integer visitorReview
//            ,Double distance
//    ) {
//        this.contentId=contentId;
//        this.title = title;
//        this.subtitle=subtitle;
//        this.contentType=contentType.toString();
//        this.rating=rating;
//        this.blogReview = blogReview;
//        this.visitorReview=visitorReview;
//        this.distance=distance;
//
//    }

    public ContentQueryResponse(String contentId, String title, String subtitle, String contentType,
                                String rating, String blogReview, String visitorReview, String distance) {
        this.contentId = Long.valueOf(contentId);
        this.title = title;
        this.subtitle=subtitle;
        this.contentType=contentType;
        this.rating = Double.valueOf(rating);
        this.blogReview = Integer.valueOf(blogReview);
        this.visitorReview=Integer.valueOf(visitorReview);
        this.distance=Double.valueOf(distance);
    }
}
