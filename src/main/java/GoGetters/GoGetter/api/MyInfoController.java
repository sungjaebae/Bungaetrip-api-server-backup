package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.article.Article;
import GoGetters.GoGetter.dto.article.ArticleResponse;
import GoGetters.GoGetter.service.ArticleService;
import GoGetters.GoGetter.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MyInfoController {
    private final ArticleService articleService;

    @GetMapping(value = "/myInfo/myArticles/{memberId}")
    public ResponseEntity readMyArticles(@PathVariable(name = "memberId") Long memberId) {
        List<Article> myArticles= articleService.findArticlesByMemberId(memberId);
        log.debug("myArticles : {}", myArticles.size());
        List<ArticleResponse> collect = myArticles.stream().map(a -> new ArticleResponse(a)).collect(Collectors.toList());
        log.debug("myArticles Dto : {}",collect);
        return ResponseUtil.successResponse(HttpStatus.OK, collect);
    }
}
