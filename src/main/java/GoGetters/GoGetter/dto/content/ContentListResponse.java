package GoGetters.GoGetter.dto.content;

import GoGetters.GoGetter.domain.content.Content;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContentListResponse {
    private Long contentId;
    private String title;

    private String content;
    private String contentType;
    private String thumbnail;
    private Double latitude;
    private Double longitude;
    private Integer likes;
    private Long review;
    private Double rating;
    private Long ratingCount;
    private String address;
    public ContentListResponse(Content content) {
        this.contentId=content.getId();
        this.title = content.getTitle();
        this.content=content.getContent();
        this.contentType=content.getContentType().toString();
        this.thumbnail=content.getThumbnail();
        this.latitude = content.getLatitude();
        this.longitude=content.getLongitude();
        this.likes = content.getLikes();
        this.review=content.getReview();
        this.rating=content.getRating();
        this.address=content.getAddress();
        this.ratingCount=content.getRatingCount();

    }
}
