package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.article.Article;
import GoGetters.GoGetter.domain.article.ArticleSortType;
import GoGetters.GoGetter.domain.article.ArticleType;
import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.dto.article.UpdateArticleRequest;
import GoGetters.GoGetter.repository.ArticleRepository;
import GoGetters.GoGetter.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ContentRepository contentRepository;
    //
    public List<Article> findArticles() {
        return articleRepository.findCreateArticles();
    }

    public Article findArticle(Long articleId) {
        return articleRepository.findArticle(articleId);

    }



    @Transactional
    public Long writeArticle(Article article) {
        return articleRepository.save(article);
    }

    @Transactional
    public Long updateArticleRequest(UpdateArticleRequest articleRequest) {
        Article findArticle = articleRepository.findArticle(articleRequest.getArticleId());

        Content destinationContent = contentRepository.findOne(articleRequest.getDestinationContentId());
        findArticle.modifyArticle(ArticleType.valueOf(articleRequest.getArticleType()),articleRequest.getDeparture(), articleRequest.getDestination(),
                destinationContent,
                articleRequest.getDate(), articleRequest.getTime(),
                articleRequest.getCurrentParticipants(), articleRequest.getTitle(), articleRequest.getContent(),
                articleRequest.getDepartureLongitude(), articleRequest.getDepartureLatitude()
                , articleRequest.getDestinationLongitude(), articleRequest.getDestinationLatitude());
        return findArticle.getId();
    }

    @Transactional
    public Long deleteArticle(Long articleId){
        Article article = articleRepository.findArticle(articleId);
        return articleRepository.deleteArticle(article);
    }

    public Long save(Article article) {
        return articleRepository.save(article);
    }

    public List<Article> findArticlesBySearchKeyword(String searchKeyword) {

        return articleRepository.findArticlesByKeyword(searchKeyword);
    }

    public List<Article> findArticlesByMemberId(Long memberId) {
        List<Article> articles = articleRepository.findArticlesByMemberId(memberId);
        return articles;
    }

    public List<Article> sortArticles(ArticleSortType sortType) {
        List<Article> sortedList=new ArrayList<>();
        switch (sortType) {
            case MEETING_DATE:
                sortedList=articleRepository.sortByMeetingDate();
                log.debug("Article Service sortedList:{}",sortedList);

        }
        return sortedList;

    }

    public List<Article> findArticlesByLocation(String location) {
        return articleRepository.findArticlesByLocation(location);
    }
}
