package GoGetters.GoGetter.db;

import GoGetters.GoGetter.domain.Article;
import GoGetters.GoGetter.domain.Gender;
import GoGetters.GoGetter.domain.Message;
import GoGetters.GoGetter.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

        public void dbInit1(){
            List<User> users=new ArrayList<>();
            Random random = new Random();

            for(int i=0;i<10;i++){
                User user = createUser(String.valueOf(i)+"@naver.com", "유저"+String.valueOf(i+1), "1234", 20, Gender.MALE);
                users.add(user);
                em.persist(user);
            }

            List<Article> articles=new ArrayList<>();
            for(int i=0;i<100;i++){
                Article article=createArticle(users.get(random.nextInt(10)),"출발지"+String.valueOf(i),
                        "도착지"+String.valueOf(i),LocalDate.of(2022,9,1)
                        ,LocalTime.of(10,20), 4/(random.nextInt(4)+1),5,
                        "제목"+String.valueOf(i),"내용" +String.valueOf(i)  );
                em.persist(article);
            }





        }

        private User createUser(String email, String nickName, String pw, Integer age, Gender gender) {
            return new User(email, nickName, pw, age, gender);
        }

        private Article createArticle(User user, String dep, String des, LocalDate date, LocalTime time,
                                      Integer current, Integer total, String title, String content) {
            return new Article(user,dep,des,date,time,current,total,title,content);
        }


    }


}
