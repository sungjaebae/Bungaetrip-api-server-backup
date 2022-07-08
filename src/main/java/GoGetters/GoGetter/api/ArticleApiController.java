package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.Article;
import GoGetters.GoGetter.dto.ArticleDto;
import GoGetters.GoGetter.dto.ArticleRequest;
import GoGetters.GoGetter.dto.Result;
import GoGetters.GoGetter.dto.TempDTO;
import GoGetters.GoGetter.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ArticleApiController {
    private final ArticleService articleService;
    //모든 글 조회
    @GetMapping(value = "/articles")
    public Result listArticle(){
        System.out.println("모든 글 조회");

        //모든 글 가져오기
        List<Article> findArticles=articleService.findArticles();

        //DTO 리스트로 변환
        List<ArticleDto> collect = findArticles.stream().map(a -> new ArticleDto(a.getId(),a.getDeparture(), a.getDestination(),
                a.getDate(), a.getTime(), a.getCurrentParticipants(), a.getTotalParticipants()
                , a.getTitle(), a.getContent())).collect(Collectors.toList());

        return new Result(collect);
    }
    //특정 게시글 조회
    @GetMapping(value = "/articles",params = "id")
    public Result readArticle(@RequestParam(value = "id") Long articleId){
        System.out.println("특정 글 조회");
        Article article = articleService.findArticle(articleId);
        ArticleDto articleDto=new ArticleDto(article);
        return new Result(articleDto);
    }

    //글 작성
    @PostMapping("/articles")
    public Result createArticle(ArticleRequest createArticleRequest,
                              @DateTimeFormat(pattern = "yyyy-MM-dd")
                                @RequestParam("date")LocalDate date,
                              @DateTimeFormat(pattern = "HH:mm:ss")
                                @RequestParam("time")LocalTime time){
        System.out.println("post");
        Article article = new Article(createArticleRequest.getDeparture(), createArticleRequest.getDestination(),
                date,time,createArticleRequest.getCurrentParticipants(), createArticleRequest.getTotalParticipants(),
                createArticleRequest.getTitle(), createArticleRequest.getContent());
        Long writeId=articleService.writeArticle(article);
        System.out.println(writeId);
        Article findArticle=articleService.findArticle(writeId);

        Map<String,Long> ret=new HashMap<>();
        ret.put("articleId", findArticle.getId());
        return new Result(ret);
    }

    //글 수정
    @PutMapping("/articles")
    public Result updateArticle(@RequestParam("id") Long articleId, ArticleRequest updateArticleRequest,
                              @DateTimeFormat(pattern = "yyyy-MM-dd")
                              @RequestParam("date")LocalDate date,
                              @DateTimeFormat(pattern = "HH:mm:ss")
                              @RequestParam("time")LocalTime time){

        ArticleDto article=new ArticleDto(updateArticleRequest,date,time);
        Long updatedId=articleService.updateArticleRequest(articleId,article);

        Map<String, Long> ret = new HashMap<>();
        ret.put("articleId",updatedId);
        return new Result(ret);
    }

    //글 삭제
    @DeleteMapping("/articles")
    public Result deleteArticle(@RequestParam("id") Long articleId){
        Long deleteId=articleService.deleteArticle(articleId);

        Map<String, Long> ret = new HashMap<>();
        ret.put("articleId", deleteId);
        return new Result(ret);

    }


}
