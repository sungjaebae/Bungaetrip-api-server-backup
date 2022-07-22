package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.Article;
import GoGetters.GoGetter.domain.Member;
import GoGetters.GoGetter.dto.ArticleDto;
import GoGetters.GoGetter.dto.ArticleRequest;
import GoGetters.GoGetter.dto.RequestDto.UpdateArticleRequest;
import GoGetters.GoGetter.dto.ResponseDto.Error;
import GoGetters.GoGetter.dto.ResponseDto.Result;
import GoGetters.GoGetter.dto.ResponseDto.Success;
import GoGetters.GoGetter.dto.returnDto.MemberReturnDto;
import GoGetters.GoGetter.service.ArticleService;
import GoGetters.GoGetter.service.MemberService;
import GoGetters.GoGetter.util.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.json.Json;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ArticleApiController {
    private final ArticleService articleService;
    private final MemberService memberService;
    //모든 글 조회
    @GetMapping(value = "/articles")
    public ResponseEntity listArticle(){
        //모든 글 가져오기
        List<Article> findArticles=articleService.findArticles();

        //DTO 리스트로 변환

        List<ArticleDto> collect = findArticles.stream()
                .map(a -> new ArticleDto(a)).collect(Collectors.toList());


        return ResponseUtil.successResponse(HttpStatus.OK,collect);
    }
    @Data
    @AllArgsConstructor
    public class ReturnObject{
        private Object article;
        Map<String,Object> member;

    }
    //특정 게시글 조회
    @GetMapping(value = "/articles/{id}")
    public ResponseEntity readArticle(@PathVariable(value = "id") Long articleId){
        System.out.println("특정 글 조회");
        System.out.println(articleId);
        Article article ;
        try {
            article = articleService.findArticle(articleId);
        } catch (InterruptedException e) {
            return ResponseUtil.errorResponse("삭제된 게시글입니다", HttpStatus.NOT_FOUND);
        }

        return ResponseUtil.successResponse(HttpStatus.OK,new ArticleDto(article));

    }

    @GetMapping(value = "/articles", params = "searchKeyword")
    public ResponseEntity readArticlesByKeyword(@RequestParam("searchKeyword") String searchKeyword) {
        List<Article> findArticles = articleService.findArticlesBySearchKeyword(searchKeyword);
        List<ArticleDto> collect = findArticles.stream()
                .map(a -> new ArticleDto(a)).collect(Collectors.toList());
        return ResponseUtil.successResponse(HttpStatus.OK,collect);
    }
    //글 작성
    @PostMapping("/articles")
    public ResponseEntity createArticle(@RequestBody ArticleRequest createArticleRequest) {

        Member member = memberService.findUser(createArticleRequest.getMemberId());
        Article article = new Article(member, createArticleRequest.getDeparture(), createArticleRequest.getDestination(),
                createArticleRequest.getDate(), createArticleRequest.getTime(),
                createArticleRequest.getCurrentParticipants(),
                createArticleRequest.getTitle(), createArticleRequest.getContent());

        Long writeId = articleService.writeArticle(article);
        Article findArticle = null;
        try {
            findArticle = articleService.findArticle(writeId);
        } catch (InterruptedException e) {
            return ResponseUtil.errorResponse("삭제된 게시글입니다", HttpStatus.NOT_FOUND);
        }

        Map<String, Long> ret = new HashMap<>();
        ret.put("articleId", findArticle.getId());
        return ResponseUtil.successResponse(HttpStatus.CREATED, ret);
    }

    //글 수정

    @PutMapping("/articles")
    public ResponseEntity updateArticle(@RequestBody UpdateArticleRequest articleRequest){
        Long updatedId= null;
        try {
            updatedId = articleService.updateArticleRequest(articleRequest);
        } catch (InterruptedException e) {
            return ResponseUtil.errorResponse("삭제된 게시글입니다", HttpStatus.NOT_FOUND);

        }

        Map<String, Long> ret = new HashMap<>();
        ret.put("articleId",updatedId);
        return ResponseUtil.successResponse(HttpStatus.OK,ret);

    }


    //글 삭제
    @DeleteMapping("/articles/{id}")
    public ResponseEntity deleteArticle(@PathVariable("id") Long articleId){
        Long deleteId= null;
        try {
            deleteId = articleService.deleteArticle(articleId);
        } catch (InterruptedException e) {
            return ResponseUtil.errorResponse("삭제된 게시글입니다", HttpStatus.NOT_FOUND);

        }

        Map<String, Long> ret = new HashMap<>();
        ret.put("articleId", deleteId);
        return ResponseUtil.successResponse(HttpStatus.OK,ret);


    }


}
