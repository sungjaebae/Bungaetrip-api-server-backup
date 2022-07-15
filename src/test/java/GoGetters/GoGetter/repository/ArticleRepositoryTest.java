package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.*;
import GoGetters.GoGetter.dto.RequestDto.UpdateArticleRequest;
//import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
@Transactional
public class ArticleRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    GenerateMock generateEntity;
    @Test
    public void  writeArticle() throws Exception{
        //given
        List<Member> members = generateEntity.createMembers();
        //when
        Article article = generateEntity.article(members.get(0), "애월시", "협재시", LocalDate.of(2022, 12, 20),
                LocalTime.of(10, 10), 2, "동행구합니다", "애월-> 협재 동행 구해요");
        Article article1 = articleRepository.findArticle(article.getId());

        //then
        Assertions.assertEquals(article.getId(),article1.getId());
        Assertions.assertEquals(article1.getTitle(),article.getTitle());
    }

    @Test
    public void 글수정() throws Exception{
        //given
        Member member = generateEntity.member("rlagudtn4510@naver.com","rlagudtn4510@naver.com",
                "호로로","1234", 20, Gender.MALE);
        Article article= generateEntity.article(member,"애월시","협재시", LocalDate.of(2022,12,20),
                LocalTime.of(10,10),2,"동행구합니다","애월-> 협재 동행 구해요"  );

        //when
        Article updatedArticle = updateArticle(member, article);

        //then
        Assertions.assertEquals(updatedArticle.getContent(),"협재-> 애월 동행 구해요");
    }

    private Article updateArticle(Member member, Article article) {
        UpdateArticleRequest fixes=new UpdateArticleRequest(article.getId(), "협재시","애월시",
                3,LocalDate.of(2022,11,18),LocalTime.of(10,00),
                "동행","협재-> 애월 동행 구해요");
        Long modifiedId = null;
        try {
            modifiedId = articleRepository.modifyArticle(fixes);
        } catch (InterruptedException e) {
            throw new RuntimeException("삭제된 게시글입니다",e);
        }

        Article ret;
        try {
            ret= articleRepository.findArticle(modifiedId);
            return ret;
        } catch (InterruptedException e) {
            throw new RuntimeException("삭제된 게시글입니다",e);
        }
    }


    @Test
    public void 글목록조회 () throws Exception{
        //given
        generateEntity.createMembers();
        generateEntity.createArticles();

        //when
        List<Article> allArticles = articleRepository.findAllArticles();

        //then
        Assertions.assertEquals(allArticles.size(),100);
    }

    @Test
    @Transactional
    public void 글삭제 () throws Exception{
        //given
        generateEntity.createMembers();
        generateEntity.createArticles();

        //when
        Long deletedArticle = articleRepository.deleteArticle(2L);
        Article deleted = articleRepository.findArticle(deletedArticle);
        //then
        Assertions.assertEquals(deleted.getStatus(), ArticleStatus.DELETE);
    }



}