package GoGetters.GoGetter.dto.content;

import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.dto.article.ArticleResponse;
import GoGetters.GoGetter.dto.image.ContentImageDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ContentWithArticlesResponse {
    private Long contentId;
    private String title;

    private String subtitle;

    private String content;

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

    private List<ArticleResponse> articles;
    private List<ContentImageDto> imageList;

    public ContentWithArticlesResponse(Content content) {
        this.contentId=content.getId();
        this.title = content.getTitle();
        this.subtitle=content.getSubtitle();
        this.content=content.getContent();
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

        this.articles = content.getArticles().stream().map(article -> new ArticleResponse(article))
                .collect(Collectors.toList());
        this.imageList = content.getImages().stream().map(image -> new ContentImageDto(image))
                .collect(Collectors.toList());
    }

}
