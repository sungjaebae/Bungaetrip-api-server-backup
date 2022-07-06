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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ArticleApiController {
    private final ArticleService articleService;
    //모든 글 조회
    @GetMapping("/articles")
    public Result listArticle(){
        //모든 글 가져오기
        List<Article> findArticles=articleService.findArticles();

        //DTO 리스트로 변환
        List<ArticleDto> collect = findArticles.stream().map(a -> new ArticleDto(a.getDeparture(), a.getDestination(),
                a.getDate(), a.getTime(), a.getCurrentParticipants(), a.getTotalParticipants()
                , a.getTitle(), a.getContent())).collect(Collectors.toList());

        return new Result(collect);
    }
    //특정 게시글 조회
    @GetMapping("/article")
    public Result readArticle(@RequestParam("id") Long articleId){
        Article article = articleService.findArticle(articleId);
        ArticleDto articleDto=new ArticleDto(article);
        return new Result(1L);
    }

    //글 작성
    @PostMapping("/articles")
    public Result createArticle(ArticleRequest createArticleRequest,
                              @DateTimeFormat(pattern = "yyyy-MM-dd")
                                @RequestParam("date")LocalDate date,
                              @DateTimeFormat(pattern = "HH:mm:ss")
                                @RequestParam("time")LocalTime time){

        Article article = new Article(createArticleRequest.getDeparture(), createArticleRequest.getDestination(),
                date,time,createArticleRequest.getCurrentParticipants(), createArticleRequest.getTotalParticipants(),
                createArticleRequest.getTitle(), createArticleRequest.getContent());
        Long writeId=articleService.writeArticle(article);
        return new Result(writeId);
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

        return new Result(updatedId);
    }

    //글 삭제
    @DeleteMapping("/articles")
    public Result deleteArticle(@RequestParam("id") Long articleId){
        Long deleteId=articleService.deleteArticle(articleId);
        return new Result(deleteId);

    }

//    @Data
//    @AllArgsConstructor
//    static class Result<T>{
//        private T data;
//    }

}
