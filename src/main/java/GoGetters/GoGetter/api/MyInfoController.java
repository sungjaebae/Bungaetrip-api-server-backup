package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.article.Article;
import GoGetters.GoGetter.dto.article.ArticleResponse;
import GoGetters.GoGetter.service.ArticleService;
import GoGetters.GoGetter.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "작성한 글 목록 조회 API",description = "회원 번호에 해당하는 회원이 작성한" +
            " 글 목록을 JSON 형태로 반환한다")
    public ResponseEntity readMyArticles(@PathVariable(name = "memberId") Long memberId) {
        List<Article> myArticles= articleService.findArticlesByMemberId(memberId);
        log.debug("myArticles : {}", myArticles.size());
        List<ArticleResponse> collect = myArticles.stream().map(a -> new ArticleResponse(a)).collect(Collectors.toList());
        log.debug("myArticles Dto : {}",collect);
        return ResponseUtil.successResponse(HttpStatus.OK, collect);
    }
}
