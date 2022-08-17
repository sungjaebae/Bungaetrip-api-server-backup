package GoGetters.GoGetter.api;

import GoGetters.GoGetter.MessageResource;
import GoGetters.GoGetter.domain.article.Article;
import GoGetters.GoGetter.domain.article.ArticleSortType;
import GoGetters.GoGetter.domain.member.Member;
import GoGetters.GoGetter.dto.article.ArticleResponse;
import GoGetters.GoGetter.dto.article.CreateArticleRequest;
import GoGetters.GoGetter.dto.article.UpdateArticleRequest;
import GoGetters.GoGetter.exception.Article.InvalidSortTypeException;
import GoGetters.GoGetter.service.ArticleService;
import GoGetters.GoGetter.service.MemberService;
import GoGetters.GoGetter.util.ResponseUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    //글 작성
    @PostMapping("")
    @Operation(summary = "게시글 생성 API",
            description= "게시글 생성에 필요한 정보인 출발지, 도착지, 현재 인원, 약속 날짜 및 시간, 제목, 내용 등을 입력하여 " +
                    "게시글을 생성합니다")
    public ResponseEntity createArticle(@RequestBody CreateArticleRequest createArticleRequest) {

        Member member = memberService.findOne(createArticleRequest.getMemberId());
        Article article = new Article(member, createArticleRequest.getDeparture(), createArticleRequest.getDestination(),
                createArticleRequest.getDate(), createArticleRequest.getTime(),
                createArticleRequest.getCurrentParticipants(),
                createArticleRequest.getTitle(), createArticleRequest.getContent());

        Long writeId = articleService.writeArticle(article);
        Article findArticle = articleService.findArticle(writeId);

        Map<String, Long> ret = new HashMap<>();
        ret.put("articleId", findArticle.getId());

        return ResponseUtil.successResponse(HttpStatus.CREATED, ret);
    }

    //특정 게시글 조회
    @GetMapping(value = "/{id}")
    @Operation(summary= "특정 게시글 조회 API", description = "사용자가 요청한 게시글 번호에 해당하는 게시글 정보를 조회합니다." +
            "게시글 번호, 출발지, 도착지, 약속 날짜 및 시간, 현재 인원, 글 생성 시간, 제목 및 내용을 JSON 형태로 반환합니다",responses = {
            @ApiResponse(responseCode = "200",description = "게시글 조회 성공",content =  @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "404",description = "존재하지 않는 리소스 접근",content =  @Content(schema = @Schema(implementation = ResponseEntity.class)))
    })
    @ApiImplicitParam(name = "id", value = "게시글 번호", dataType = "Long", required = true, example = "1", paramType = "path")
    public ResponseEntity readArticle(@PathVariable(value = "id")  Long articleId) {

        Article article;
        article = articleService.findArticle(articleId);
        ArticleResponse ret = new ArticleResponse(article);
        return ResponseUtil.successResponse(HttpStatus.OK, ret);

    }

    //글 수정
    @PutMapping("")
    @Operation(summary = "특정 게시글 수정 API", description = "게시글 수정에 필요한 정보인 게시글 번호, " +
            "출발지, 도착지, 현재 인원, 약속 날짜 및 시간, 제목, 내용 등을 입력하여 " +
            "게시글을 생성합니다")
    public ResponseEntity updateArticle(@RequestBody  UpdateArticleRequest articleRequest) {

        Long updatedId = articleService.updateArticleRequest(articleRequest);

        Map<String, Long> ret = new HashMap<>();
        ret.put("articleId", updatedId);
        return ResponseUtil.successResponse(HttpStatus.OK, ret);

    }

    //글 삭제
    @DeleteMapping("/{id}")
    @Operation(summary= "특정 게시글 삭제 API", description = "게시글 번호에 해당하는 게시글을 삭제하고 " +
            "해당 게시글의 번호를 반환합니다")
    @ApiImplicitParam(name = "id", value = "게시글 번호", dataType = "Long", required = true, example = "1", paramType = "path")
    public ResponseEntity deleteArticle(@PathVariable("id")  Long articleId) {
        Long deleteId = articleService.deleteArticle(articleId);

        Map<String, Long> ret = new HashMap<>();
        ret.put("articleId", deleteId);
        return ResponseUtil.successResponse(HttpStatus.OK, ret);


    }

    //모든 글 조회
    @GetMapping(value = "")
    @Operation(summary = "모든 게시글 목록 조회 API", description = "회원들이 작성한 게시글 목록을 JSON 형태로 반환합니다")
    public ResponseEntity listArticle() {
        //모든 글 가져오기
        List<Article> findArticles = articleService.findArticles();

        //DTO 리스트로 변환

        List<ArticleResponse> collect = findArticles.stream()
                .map(a -> new ArticleResponse(a)).collect(Collectors.toList());

        return ResponseUtil.successResponse(HttpStatus.OK, collect);
    }


    @GetMapping(value = "", params = "searchKeyword")
    @Operation(summary = "게시글 검색 API", description = "사용자가 입력한 검색어를 통해 게시글을 검색합니다. 이때, 제목, 내용" +
            ", 출발지 및 도착지에 해당 검색어가 들어가 있는 모든 게시글을 반환합니다")
    @ApiImplicitParam(name = "searchKeyword", value = "검색 키워드", dataType = "String", required = true, paramType = "query")
    public ResponseEntity readArticlesByKeyword(@RequestParam(value = "searchKeyword")  String searchKeyword) {
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


    @GetMapping(value = "/sort", params = "sortType")
    @Operation(summary = "게시글 정렬 API", description = "최신순, 마감 임박순 등의 정렬 기준을 통해 게시글을 정렬하여" +
            " JSON 형태로 반환한다")
    @ApiImplicitParam(name = "sortType", value = "게시글 분류 타입", dataType = "String", required = true, paramType = "query")
    public ResponseEntity sortedArticleList(@RequestParam("sortType")  String sortType) {
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
