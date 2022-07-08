package GoGetters.GoGetter.db;

import GoGetters.GoGetter.domain.*;
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

//    @PostConstruct
//    public void init(){
//        initService.dbInitUser();
//        initService.dbInitArticle();
//        initService.dbInitMessage();
//    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

        private List<User> users=new ArrayList<>();
        private List<Article> articles=new ArrayList<>();
        private List<Message> messages=new ArrayList<>();
        private Random random = new Random();

        public void dbInitUser(){
            for(int i=0;i<10;i++){
                User user = createUser(String.valueOf(i)+"@naver.com", "유저"+String.valueOf(i+1), "1234", 20, Gender.MALE);
                em.persist(user);
                users.add(user);
            }
            System.out.println("userInit");
            System.out.println(users.get(0).getId());

        }
        public void dbInitArticle(){
            System.out.println("article db");
            for(int i=0;i<100;i++){
                Article article=createArticle(users.get(random.nextInt(10)),"출발지"+String.valueOf(i),
                        "도착지"+String.valueOf(i),LocalDate.of(2022,9,1)
                        ,LocalTime.of(10,20), 4/(random.nextInt(4)+1),5,
                        "제목"+String.valueOf(i),"내용" +String.valueOf(i)  );
                em.persist(article);
                articles.add(article);
            }
        }
        public void dbInitMessage(){
            List<Sender> senders=new ArrayList<>();
            List<Receiver> receivers=new ArrayList<>();
            System.out.println("message start");
            for(int i=0;i<10;i++){
                User user=users.get(i);
                System.out.println(user.getId());
                Sender sender=new Sender(user);
                Receiver receiver=new Receiver(user);
                em.persist(sender);
                em.persist(receiver);
                senders.add(sender);
                receivers.add(receiver);
            }

            for(int i=0;i<100;i++){
                Message message=new Message("내용 "+String.valueOf(i));
                int sendIndex=random.nextInt(10 );
                message.setUsers(senders.get(sendIndex), receivers.get(9 - sendIndex));
                em.persist(message);
                messages.add(message);
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
