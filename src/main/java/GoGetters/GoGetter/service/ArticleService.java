package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.Article;
import GoGetters.GoGetter.dto.ArticleDto;
import GoGetters.GoGetter.dto.RequestDto.UpdateArticleRequest;
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

    public Article findArticle(Long articleId) throws InterruptedException {
        return articleRepository.findArticle(articleId);
    }

    @Transactional
    public Long writeArticle(Article article) {
        return articleRepository.save(article);
    }

    @Transactional
    public Long updateArticleRequest(UpdateArticleRequest article) throws InterruptedException {
        return articleRepository.modifyArticle(article);

    }

    @Transactional
    public Long deleteArticle(Long articleId) throws InterruptedException {

        return articleRepository.deleteArticle(articleId);
    }

    public Long save(Article article) {
        return articleRepository.save(article);
    }
}
