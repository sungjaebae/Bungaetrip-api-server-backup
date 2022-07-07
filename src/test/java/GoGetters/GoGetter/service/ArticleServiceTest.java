package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.Article;
import GoGetters.GoGetter.domain.Gender;
import GoGetters.GoGetter.domain.User;
import GoGetters.GoGetter.dto.ArticleDto;
import GoGetters.GoGetter.dto.ArticleRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    @Test
    public void 글작성하기 () throws Exception{
        //given
        User user = new User("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        userService.join(user);

        //when
        Article article=new Article(user,"애월","협재", LocalDate.of(2022,10,10),
                LocalTime.of(10,10),2,4,"동행구해요","애월->협재");
        Long saveId=articleService.save(article);

        //then
        Article findArticle = articleService.findArticle(saveId);
        assertEquals(findArticle.getId(),article.getId());
        assertEquals(findArticle.getWriter().getId(), user.getId());
    }

    @Test
    public void 글수정하기 () throws Exception{
        //given
        User user = new User("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        userService.join(user);

        //when
        for(int i=0;i<10;i++){
            Article article=new Article(user,"애월"+i,"협재", LocalDate.of(2022,10,10),
                    LocalTime.of(10,10),2,4,"동행구해요","애월->협재");
            articleService.save(article);
        }

        //when
        ArticleRequest articleRequest=new ArticleRequest("출발지","도착지",
                4,4,"제목","내용");
        ArticleDto article=new ArticleDto(articleRequest,LocalDate.of(2022,10,01),
                LocalTime.of(8,8));
        Long updateId = articleService.updateArticleRequest(1L, article);

//        //then
        Article findArticle = articleService.findArticle(updateId);
        assertEquals(findArticle.getDeparture(), "출발지");
        assertEquals(findArticle.getId(),1L);
        assertEquals(findArticle.getDestination(),"도착지");
    }

    @Test
    public void 글삭제하기 () throws Exception{
        //given
        User user = new User("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        userService.join(user);

        //when
        for(int i=0;i<10;i++){
            Article article=new Article(user,"애월"+i,"협재", LocalDate.of(2022,10,10),
                    LocalTime.of(10,10),2,4,"동행구해요","애월->협재");
            articleService.save(article);
        }

        //when
        Long deleteId=articleService.deleteArticle(2L);
        List<Article> articles = articleService.findArticles();

        //then
        assertEquals(articles.size(),9);
    }

    @Test
    public void 글목록조회하기 () throws Exception{
        //given
        User user = new User("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        userService.join(user);

        //when
        for(int i=0;i<10;i++){
            Article article=new Article(user,"애월"+i,"협재", LocalDate.of(2022,10,10),
                    LocalTime.of(10,10),2,4,"동행구해요","애월->협재");
            articleService.save(article);
        }

        //when
        List<Article> articles = articleService.findArticles();

        //then
        assertEquals(articles.size(),10);
        assertEquals(articles.get(0).getDeparture(),"애월9");
        assertEquals(articles.get(1).getDeparture(),"애월8");
        assertEquals(articles.get(9).getDeparture(),"애월0");
    }



}