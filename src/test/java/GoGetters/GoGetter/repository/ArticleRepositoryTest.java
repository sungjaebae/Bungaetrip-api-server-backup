package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.Article;
import GoGetters.GoGetter.domain.Gender;
import GoGetters.GoGetter.domain.Member;
import GoGetters.GoGetter.dto.RequestDto.UpdateArticleRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
public class ArticleRepositorytyTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    @Transactional
    public void  writeArticle() throws Exception{
        //given
        System.out.println("helo");
//        Member member = new Member("rlagudtn4510@naver.com", "호로로","1234", 20, Gender.MALE);
//        memberRepository.save(member);
//
//        //when
//        Article article=new Article(member,"애월시","협재시", LocalDate.of(2022,12,20),
//                LocalTime.of(10,10),2,"동행구합니다","애월-> 협재 동행 구해요"  );
//        Long saveId = articleRepository.save(article);
//        Article article1 = articleRepository.findArticle(saveId);
//
//        //then
//        Assertions.assertThat(article1.getTitle()).isEqualTo(article.getTitle());
    }

    @Test
    @Transactional
    public void 글수정() throws Exception{
        //given
        Member member = new Member("rlagudtn4510@naver.com", "호로로","1234", 20, Gender.MALE);
        memberRepository.save(member);
        Article article=new Article(member,"애월시","협재시", LocalDate.of(2022,12,20),
                LocalTime.of(10,10),2,"동행구합니다","애월-> 협재 동행 구해요"  );
        Long saveId = articleRepository.save(article);
        //when
        UpdateArticleRequest fixes=new UpdateArticleRequest(article.getId(), "협재시","애월시",
                3,LocalDate.of(2022,11,18),LocalTime.of(10,00),
                "동행","협재-> 애월 동행 구해요");
        Long modifiedId = articleRepository.modifyArticle(fixes);
        Article modified = articleRepository.findArticle(modifiedId);

        //then
        Assertions.assertThat(modified.getContent()).isEqualTo("협재-> 애월 동행 구해요");
    }

    @Test
    public void 글목록조회 () throws Exception{
        //given
        Member member = new Member("rlagudtn4510@naver.com", "호로로","1234", 20, Gender.MALE);
        memberRepository.save(member);


        //when
        for(int i=0;i<10;i++){
            Article article=new Article(member,"애월시","협재시", LocalDate.of(2022,12,20),
                    LocalTime.of(10,10),2,"동행구합니다","애월-> 협재 동행 구해요"  );
            articleRepository.save(article);

        }

        //then
        List<Article> allArticles = articleRepository.findAllArticles();
        Assertions.assertThat(allArticles.size()).isEqualTo(10);
    }

    @Test
    @Transactional
    public void 글삭제 () throws Exception{
        //given
//        Member member = new Member("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
//        memberRepository.save(member);
//        Member member1 = new Member("rlagudtn@naver.com", "호로로","1234", 21, Gender.MALE);
//        memberRepository.save(member1);
//        for(int i=0;i<10;i++){
//            Article article=new Article(member,"애월시","협재시"
//                    , LocalDate.of(2022,12,20),
//                    LocalTime.of(10,10),2,"동행구합니다","애월-> 협재 동행 구해요"  );
//
//            articleRepository.save(article);
//        }
//
//        //when
//        Long deletedArticle = articleRepository.deleteArticleState(2L);
//        Article deleted = articleRepository.findArticle(deletedArticle);
//        //then
//        Assertions.assertThat(deleted.getStatus()).isEqualTo(ArticleStatus.DELETE);
    }




}