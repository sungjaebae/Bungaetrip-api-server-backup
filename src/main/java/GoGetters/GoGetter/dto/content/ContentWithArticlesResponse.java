package GoGetters.GoGetter.dto.content;

import GoGetters.GoGetter.domain.article.Article;
import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.dto.article.ArticleResponse;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ContentWithArticlesResponse {
    private Long contentId;
    private String title;

    private String content;
    private String thumbnail;
    private Double latitude;
    private Double longitude;
    private Integer likes;
    private Long review;
    private Long rating;
    private String address;

    private List<ArticleResponse> articles;

    public ContentWithArticlesResponse(Content content, List<Article> articles) {
        this.contentId=content.getId();
        this.title = content.getTitle();
        this.content=content.getContent();
        this.thumbnail=content.getThumbnail();
        this.latitude = content.getLatitude();
        this.longitude=content.getLongitude();
        this.likes = content.getLikes();
        this.review=content.getReview();
        this.rating=content.getRating();
        this.address=content.getAddress();
        this.articles = articles.stream().map(article -> new ArticleResponse(article)).collect(Collectors.toList());
    }

}
