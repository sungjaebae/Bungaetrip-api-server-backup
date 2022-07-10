package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.Article;
import GoGetters.GoGetter.domain.ArticleStatus;
import GoGetters.GoGetter.domain.Gender;
import GoGetters.GoGetter.domain.User;
import GoGetters.GoGetter.dto.ArticleDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    @Transactional
    public void  글작성() throws Exception{
        //given
        User user = new User("rlagudtn4510@naver.com", "호로로","1234", 20, Gender.MALE);
        userRepository.save(user);

        //when
        Article article=new Article(user,"애월시","협재시", LocalDate.of(2022,12,20),
                LocalTime.of(10,10),2,"동행구합니다","애월-> 협재 동행 구해요"  );
        Long saveId = articleRepository.save(article);
        Article article1 = articleRepository.findArticle(saveId);

        //then
        Assertions.assertThat(article1.getTitle()).isEqualTo(article.getTitle());
    }

    @Test
    @Transactional
    public void 글수정() throws Exception{
        //given
        User user = new User("rlagudtn4510@naver.com", "호로로","1234", 20, Gender.MALE);
        userRepository.save(user);
        Article article=new Article(user,"애월시","협재시", LocalDate.of(2022,12,20),
                LocalTime.of(10,10),2,"동행구합니다","애월-> 협재 동행 구해요"  );
        Long saveId = articleRepository.save(article);
        //when
        ArticleDto fixes=new ArticleDto(article.getId(), "협재시","애월시",
                LocalDate.of(2022,11,18),LocalTime.of(10,00),
                3,"동행","협재-> 애월 동행 구해요");
        Long modifiedId = articleRepository.modifyArticle(1L, fixes);
        Article modified = articleRepository.findArticle(modifiedId);

        //then
        Assertions.assertThat(modified.getContent()).isEqualTo("협재-> 애월 동행 구해요");
    }

    @Test
    public void 글목록조회 () throws Exception{
        //given
        User user = new User("rlagudtn4510@naver.com", "호로로","1234", 20, Gender.MALE);
        userRepository.save(user);


        //when
        for(int i=0;i<10;i++){
            Article article=new Article(user,"애월시","협재시", LocalDate.of(2022,12,20),
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
        User user = new User("rlagudtn4510@naver.com","호로로", "1234", 20, Gender.MALE);
        userRepository.save(user);
        User user1 = new User("rlagudtn@naver.com", "호로로","1234", 21, Gender.MALE);
        userRepository.save(user1);
        for(int i=0;i<10;i++){
            Article article=new Article(user,"애월시","협재시", LocalDate.of(2022,12,20),
                    LocalTime.of(10,10),2,"동행구합니다","애월-> 협재 동행 구해요"  );

            articleRepository.save(article);
        }

        //when
        Long deletedArticle = articleRepository.deleteArticleState(2L);
        Article deleted = articleRepository.findArticle(deletedArticle);
        //then
        Assertions.assertThat(deleted.getStatus()).isEqualTo(ArticleStatus.DELETE);
    }




}