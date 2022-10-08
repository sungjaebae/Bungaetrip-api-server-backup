package GoGetters.GoGetter.service;

import GoGetters.GoGetter.MessageResource;
import GoGetters.GoGetter.domain.article.Article;
import GoGetters.GoGetter.domain.article.ArticleSortType;
import GoGetters.GoGetter.domain.article.ArticleType;
import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.dto.article.UpdateArticleRequest;
import GoGetters.GoGetter.exception.Article.NoSuchArticleException;
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
        List<Article> article = articleRepository.findArticle(articleId);
        if(article.isEmpty())
            throw new NoSuchArticleException(MessageResource.articleNotExist);
        return article.get(0);
    }



    @Transactional
    public Long writeArticle(Article article) {
        return articleRepository.save(article);
    }

    @Transactional
    public Long updateArticleRequest(UpdateArticleRequest articleRequest) {
        List<Article> findArticle = articleRepository.findArticle(articleRequest.getArticleId());
        if (findArticle.isEmpty()) {
            throw new NoSuchArticleException(MessageResource.articleNotExist);
        }
        Content destinationContent = contentRepository.findOne(articleRequest.getDestinationContentId());
        findArticle.get(0).modifyArticle(ArticleType.valueOf(articleRequest.getArticleType()),articleRequest.getDeparture(), articleRequest.getDestination(),
                destinationContent,
                articleRequest.getDate(), articleRequest.getTime(),
                articleRequest.getCurrentParticipants(), articleRequest.getTitle(), articleRequest.getContent(),
                articleRequest.getDepartureLongitude(), articleRequest.getDepartureLatitude()
                , articleRequest.getDestinationLongitude(), articleRequest.getDestinationLatitude());
        return findArticle.get(0).getId();
    }

    @Transactional
    public Long deleteArticle(Long articleId){
        List<Article> article = articleRepository.findArticle(articleId);
        if(article.isEmpty())
            throw new NoSuchArticleException(MessageResource.articleNotExist);
        return articleRepository.deleteArticle(article.get(0));
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
