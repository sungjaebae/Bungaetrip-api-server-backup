package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.Article;
import GoGetters.GoGetter.domain.Gender;
import GoGetters.GoGetter.domain.Member;
import GoGetters.GoGetter.dto.RequestDto.UpdateArticleRequest;
import GoGetters.GoGetter.repository.GenerateMock;
import org.junit.jupiter.api.Assertions;
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
    private MemberService memberService;

    @Autowired GenerateMock generateMock;
    @Test
    public void 글작성하기 () throws Exception{
        //given
        Member member = new Member("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        memberService.join(member);

        //when
        Article article=new Article(member,"애월","협재", LocalDate.of(2022,10,10),
                LocalTime.of(10,10),2,"동행구해요","애월->협재");
        Long saveId=articleService.save(article);

        //then
        Article findArticle = articleService.findArticle(saveId);
        assertEquals(findArticle.getId(),article.getId());
        assertEquals(findArticle.getWriter().getId(), member.getId());
    }

    @Test
    public void 글수정하기 () throws Exception{
        //given
        Member member = new Member("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        memberService.join(member);

        //when
        for(int i=0;i<10;i++){
            Article article=new Article(member,"애월"+i,"협재", LocalDate.of(2022,10,10),
                    LocalTime.of(10,10),2,"동행구해요","애월->협재");
            articleService.save(article);
        }

        //when

        UpdateArticleRequest request=new UpdateArticleRequest(1L
                ,"출발지","도착지",4
                ,LocalDate.of(2022,10,01),LocalTime.of(8,8)
                ,"수정된 제목","수정된 내용");
        System.out.println("수정아이디");
        System.out.println(request.getArticleId());
        Long updateId = articleService.updateArticleRequest(request);

        //then
        Article findArticle = articleService.findArticle(updateId);
        assertEquals(findArticle.getDeparture(), "출발지");
        assertEquals(findArticle.getId(),1L);
        assertEquals(findArticle.getDestination(),"도착지");
    }

    @Test
    @Rollback(value = false)
    public void 글삭제하기 () throws Exception{
        //given
        Member member = new Member("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        memberService.join(member);

        //when
        for(int i=0;i<10;i++){
            Article article=new Article(member,"애월"+i,"협재", LocalDate.of(2022,10,10),
                    LocalTime.of(10,10),2,"동행구해요","애월->협재");
            articleService.save(article);
        }
//
//        //when
//        Long deleteId=articleService.deleteArticle(2L);
//        List<Article> articles = articleService.findArticles();
//
//        //then
//        assertEquals(articles.size(),9);
    }

    @Test
    public void 글목록조회하기 () throws Exception{
        //given
        Member member = new Member("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        memberService.join(member);

        //when
        for(int i=0;i<10;i++){
            Article article=new Article(member,"애월"+i,"협재", LocalDate.of(2022,10,10),
                    LocalTime.of(10,10),2,"동행구해요","애월->협재");
            articleService.save(article);
        }

        //when
        List<Article> articles = articleService.findArticles();

        //then
        assertEquals(articles.size(),10);
        assertEquals(articles.get(0).getDeparture(),"애월0");
        assertEquals(articles.get(1).getDeparture(),"애월1");
        assertEquals(articles.get(9).getDeparture(),"애월9");
    }

    @Test
    public void 검색하기 () throws Exception{
        //given
        List<Member> members = generateMock.createMembers();
        List<Article> articles = generateMock.createArticles();

        //when
        List<Article> findArticles = articleService.findArticlesBySearchKeyword("입니다");

        //then
        Assertions.assertEquals(100,articles.size());
    }

}