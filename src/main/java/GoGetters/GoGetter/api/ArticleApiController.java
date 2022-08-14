package GoGetters.GoGetter.api;

import GoGetters.GoGetter.MessageResource;
import GoGetters.GoGetter.domain.article.Article;
import GoGetters.GoGetter.domain.article.ArticleSortType;
import GoGetters.GoGetter.domain.member.Member;
import GoGetters.GoGetter.dto.article.ArticleResponse;
import GoGetters.GoGetter.dto.article.ArticleRequest;
import GoGetters.GoGetter.dto.article.UpdateArticleRequest;
import GoGetters.GoGetter.exception.Article.InvalidSortTypeException;
import GoGetters.GoGetter.service.ArticleService;
import GoGetters.GoGetter.service.MemberService;
import GoGetters.GoGetter.util.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "articles")
public class ArticleApiController {
    private final ArticleService articleService;
    private final MemberService memberService;

    //모든 글 조회

        @GetMapping(value = "", params ={} )
        public ResponseEntity listArticle() {
            //모든 글 가져오기
            List<Article> findArticles = articleService.findArticles();

            //DTO 리스트로 변환

            List<ArticleResponse> collect = findArticles.stream()
                    .map(a -> new ArticleResponse(a)).collect(Collectors.toList());

            log.info("Log ArticleController | get | /articles | response data : {}", collect);
            return ResponseUtil.successResponse(HttpStatus.OK, collect);
        }


    @GetMapping(value = "", params = "searchKeyword")
    public ResponseEntity readArticlesByKeyword(@RequestParam("searchKeyword") String searchKeyword) {
        List<Article> findArticles = articleService.findArticlesBySearchKeyword(searchKeyword);
        List<ArticleResponse> collect = findArticles.stream()
                .map(a -> new ArticleResponse(a)).collect(Collectors.toList());
        log.info("Log ArticleController | get | /articles?searchKeyword | request: {} | response data: {}", searchKeyword, collect);

        return ResponseUtil.successResponse(HttpStatus.OK, collect);
    }
    @Data
    @AllArgsConstructor
    public class ReturnObject {
        private Object article;
        Map<String, Object> member;

    }

    //특정 게시글 조회
    @GetMapping(value = "/{id}")
    public ResponseEntity readArticle(@PathVariable(value = "id") Long articleId) {
        Article article;
        article = articleService.findArticle(articleId);

        ArticleResponse ret = new ArticleResponse(article);
        log.info("Log ArticleController | get | /articles/{id} | request: {} | response data: {}", articleId, ret);

        return ResponseUtil.successResponse(HttpStatus.OK, ret);

    }



    //글 작성
    @PostMapping("")
    public ResponseEntity createArticle(@RequestBody ArticleRequest createArticleRequest) {

        Member member = memberService.findOne(createArticleRequest.getMemberId());
        Article article = new Article(member, createArticleRequest.getDeparture(), createArticleRequest.getDestination(),
                createArticleRequest.getDate(), createArticleRequest.getTime(),
                createArticleRequest.getCurrentParticipants(),
                createArticleRequest.getTitle(), createArticleRequest.getContent());

        Long writeId = articleService.writeArticle(article);
        Article findArticle = articleService.findArticle(writeId);

        Map<String, Long> ret = new HashMap<>();
        ret.put("articleId", findArticle.getId());

        log.info("Log ArticleController | post | /articles | request: {} | response data: {}", createArticleRequest, ret);
        return ResponseUtil.successResponse(HttpStatus.CREATED, ret);
    }

    //글 수정

    @PutMapping("")
    public ResponseEntity updateArticle(@RequestBody UpdateArticleRequest articleRequest) {

        Long updatedId = articleService.updateArticleRequest(articleRequest);

        Map<String, Long> ret = new HashMap<>();
        ret.put("articleId", updatedId);
        log.info("Log ArticleController | put | /articles | request: {} | response data : {}", articleRequest, ret);
        return ResponseUtil.successResponse(HttpStatus.OK, ret);

    }


    //글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity deleteArticle(@PathVariable("id") Long articleId) {
        Long deleteId = articleService.deleteArticle(articleId);

        Map<String, Long> ret = new HashMap<>();
        ret.put("articleId", deleteId);
        log.info("Log ArticleController | delete | /articles | request: {} | response data: {}", articleId, ret);
        return ResponseUtil.successResponse(HttpStatus.OK, ret);


    }

    @GetMapping(value = "/sort", params = "sortType")
    public ResponseEntity sortedArticleList(@RequestParam("sortType") String sortType) {
        checkSortType(sortType);

        ArticleSortType type = ArticleSortType.valueOf(sortType);
        List<Article> sortedArticles = articleService.sortArticles(type);
        List<ArticleResponse> collect = sortedArticles.stream()
                .map(a -> new ArticleResponse(a)).collect(Collectors.toList());

        log.info("Article Controller sortedList:{}", collect);
        return ResponseUtil.successResponse(HttpStatus.OK, collect);
    }

    private void checkSortType(String sortType) {
        switch (sortType) {
            case "MEETING_DATE":
                break;
            default:
                throw new InvalidSortTypeException(MessageResource.invalidArticleSortType);
        }
    }
}
