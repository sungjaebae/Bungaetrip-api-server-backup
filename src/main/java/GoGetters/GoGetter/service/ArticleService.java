package GoGetters.GoGetter.service;

import GoGetters.GoGetter.MessageResource;
import GoGetters.GoGetter.domain.Article;
import GoGetters.GoGetter.domain.ArticleSortType;
import GoGetters.GoGetter.dto.RequestDto.UpdateArticleRequest;
import GoGetters.GoGetter.exception.Article.NoSuchArticleException;
import GoGetters.GoGetter.repository.ArticleRepository;
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
    public Long updateArticleRequest(UpdateArticleRequest article) {
        List<Article> findArticle = articleRepository.findArticle(article.getArticleId());
        if (findArticle.isEmpty()) {
            throw new NoSuchArticleException(MessageResource.articleNotExist);
        }
        return articleRepository.modifyArticle(findArticle.get(0),article);

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
}
