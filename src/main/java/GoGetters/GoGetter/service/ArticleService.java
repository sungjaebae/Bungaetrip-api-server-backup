package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.Article;
import GoGetters.GoGetter.dto.ArticleDto;
import GoGetters.GoGetter.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    //
    public List<Article> findArticles() {
        return new ArrayList<>();
    }

    public Article findArticle(Long aLong) {
        return new Article();
    }

    public Long writeArticle(Article article) {
        return 0L;
    }

    public Long updateArticleRequest(Long articleId,ArticleDto article) {
        return articleId;

    }

    public Long deleteArticle(Long articleId) {
        return 3L;
    }
}
