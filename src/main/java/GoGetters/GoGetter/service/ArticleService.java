package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.Article;
import GoGetters.GoGetter.dto.ArticleDto;
import GoGetters.GoGetter.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

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
    public Long updateArticleRequest(Long articleId,ArticleDto article) {
        return articleRepository.modifyArticle(articleId, article);

    }

    @Transactional
    public Long deleteArticle(Long articleId) {

        return articleRepository.deleteArticleState(articleId);
    }

    public Long save(Article article) {
        return articleRepository.save(article);
    }
}
