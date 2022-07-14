package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.Article;
import GoGetters.GoGetter.domain.Member;
import GoGetters.GoGetter.dto.ArticleDto;
import GoGetters.GoGetter.dto.ArticleRequest;
import GoGetters.GoGetter.dto.RequestDto.UpdateArticleRequest;
import GoGetters.GoGetter.dto.Result;
import GoGetters.GoGetter.service.ArticleService;
import GoGetters.GoGetter.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public Result listArticle(){
        System.out.println("모든 글 조회");

        //모든 글 가져오기
        List<Article> findArticles=articleService.findArticles();

        //DTO 리스트로 변환
        List<ArticleDto> collect = findArticles.stream().map(a -> new ArticleDto(a.getId(),a.getDeparture(), a.getDestination(),
                a.getDate(), a.getTime(), a.getCurrentParticipants()
//                , a.getTotalParticipants()
                , a.getTitle(), a.getContent())).collect(Collectors.toList());

        return new Result(collect);
    }
    //특정 게시글 조회
    @GetMapping(value = "/articles/{id}")
    public Result readArticle(@PathVariable(value = "id") Long articleId){
        System.out.println("특정 글 조회");
        System.out.println(articleId);
        Article article = articleService.findArticle(articleId);
        ArticleDto articleDto=new ArticleDto(article);
        return new Result(articleDto);
    }

    //글 작성
    @PostMapping("/articles")
    public Result createArticle(@RequestBody ArticleRequest createArticleRequest) {
        Member member = memberService.findUser(createArticleRequest.getUserId());
        Article article = new Article(member,createArticleRequest.getDeparture(), createArticleRequest.getDestination(),
                createArticleRequest.getDate(),createArticleRequest.getTime(),
                createArticleRequest.getCurrentParticipants(),
                createArticleRequest.getTitle(), createArticleRequest.getContent());

        Long writeId=articleService.writeArticle(article);
        Article findArticle=articleService.findArticle(writeId);

        Map<String,Long> ret=new HashMap<>();
        ret.put("articleId", findArticle.getId());
        return new Result(ret);
    }

    //글 수정

    @PutMapping("/articles")
    public Result updateArticle(@RequestBody UpdateArticleRequest articleRequest){
        System.out.println("articleId");
        System.out.println(articleRequest.getDeparture());
        System.out.println(articleRequest.getArticleId());
        Long updatedId=articleService.updateArticleRequest(articleRequest);

        Map<String, Long> ret = new HashMap<>();
        ret.put("articleId",updatedId);
        return new Result(ret);
    }


    //글 삭제
    @DeleteMapping("/articles/{id}")
    public Result deleteArticle(@PathVariable("id") Long articleId){
        Long deleteId=articleService.deleteArticle(articleId);

        Map<String, Long> ret = new HashMap<>();
        ret.put("articleId", deleteId);
        return new Result(ret);

    }


}
