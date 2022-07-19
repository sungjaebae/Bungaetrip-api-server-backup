package GoGetters.GoGetter.db;

import GoGetters.GoGetter.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
//
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
        private final PasswordEncoder pe;
        private List<Member> members =new ArrayList<>();
        private List<Article> articles=new ArrayList<>();
        private List<Message> messages=new ArrayList<>();
        private Random random = new Random();

        public void dbInitUser(){
            for(int i=0;i<10;i++){
                Member member = createUser(String.valueOf(i)+"@naver.com", "유저"+String.valueOf(i+1)
                        , pe.encode("1234"), 20, Gender.MALE);

                em.persist(member);
                members.add(member);
            }
            System.out.println("userInit");

        }
        public void dbInitArticle(){
            System.out.println("article db");
            for(int i=0;i<100;i++){
                Article article=createArticle(members.get(random.nextInt(10)),"출발지"+String.valueOf(i),
                        "도착지"+String.valueOf(i),LocalDate.of(2022,9,1)
                        ,LocalTime.of(10,20), 4/(random.nextInt(4)+1),
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
                Member member = members.get(i);
                System.out.println(member.getId());
                Sender sender=new Sender(member);
                Receiver receiver=new Receiver(member);
                em.persist(sender);
                em.persist(receiver);
                senders.add(sender);
                receivers.add(receiver);
            }

            for(int i=0;i<100;i++){
                Message message=new Message("내용 "+String.valueOf(i));
                int sendIndex=random.nextInt(10 );
                message.setMembers(senders.get(sendIndex), receivers.get(9 - sendIndex));
                em.persist(message);
                messages.add(message);
            }
        }
        private Member createUser(String email, String nickname, String pw, Integer age, Gender gender) {
            return new Member(email,email, pw, nickname, age, gender);
        }

        private Article createArticle(Member member, String dep, String des, LocalDate date, LocalTime time,
                                      Integer current,
//                                      Integer total,
                                      String title, String content) {
            return new Article(member,dep,des,date,time,current,title,content);
        }


    }


}
