package GoGetters.GoGetter.dto.content;

import GoGetters.GoGetter.domain.content.Content;
import lombok.Data;

@Data
public class ContentResponseInArticle {
    private Long contentId;
    private String title;

    private String contentType;

    private Double latitude;
    private Double longitude;

    private String address;

    private Double rating;

    private Integer blogReview;

    private Integer visitorReview;

    private String phoneNumber;

    private String naverId;

    private String contentDetailUrl;

    public ContentResponseInArticle(Content content) {
        this.contentId=content.getId();
        this.title = content.getTitle();
        this.contentType=content.getContentType().toString();

        this.latitude = content.getLatitude();
        this.longitude=content.getLongitude();
        this.address=content.getAddress();

        this.rating=content.getRating();
        this.blogReview=content.getBlogReview();
        this.visitorReview=content.getVisitorReview();

        this.phoneNumber=content.getPhoneNumber();
        this.naverId=content.getNaverId();
        this.contentDetailUrl=content.getContentDetailUrl();
    }
}
