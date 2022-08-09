package GoGetters.GoGetter.db;

import GoGetters.GoGetter.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class InitDb {
    private final InitService initService;
    
    @Value("${spring.profiles.active:}") 
    private String activeProfile;

   @PostConstruct
   public void init(){
//       initService.dbInitUser();
    log.info(activeProfile);
    if (activeProfile.equals("Development"))
    {
        log.info("Db Initializer activated22");
        initService.getMembersFromDB();
        initService.dbInitArticle();
        initService.dbInitMessage();
    }
   }
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

        public void getMembersFromDB() {
            for (int i = 1; i <= 10; i++) {

                Member member = em.find(Member.class,Long.valueOf(i));
                log.debug("member id : {}",member.getId());

                members.add(member);
            }

            log.debug("members size: {}",members.size());
        }
        public void dbInitUser(){
            String description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum";
            for(int i=0;i<10;i++){
                Member member = createUser(String.valueOf(i)+"@naver.com", "유저"+String.valueOf(i+1)
                        , pe.encode("1234"), 20, Gender.MALE,i+" "+description);

                em.persist(member);
                members.add(member);
            }

        }
        public void dbInitArticle(){
            for(int i=0;i<100;i++){
                Article article=createArticle(members.get(random.nextInt(10)),"출발지"+String.valueOf(i),
                        "도착지"+String.valueOf(i),LocalDate.of(2020+i%5,i%12+1,i%28+1)
                        ,LocalTime.of(i%23+1,i%60), 4/(random.nextInt(4)+1),
                        "제목"+String.valueOf(i),"내용" +String.valueOf(i)  );
                em.persist(article);
                articles.add(article);
            }
        }
        public void dbInitMessage(){
            List<Sender> senders=new ArrayList<>();
            List<Receiver> receivers=new ArrayList<>();
            for(int i=0;i<10;i++){
                Member member = members.get(i);
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
        private Member createUser(String email, String nickname, String pw, Integer age, Gender gender,String description) {
            return new Member(email,email, pw, nickname, age, gender,description);
        }

        private Article createArticle(Member member, String dep, String des, LocalDate date, LocalTime time,
                                      Integer current,
//                                      Integer total,
                                      String title, String content) {
            return new Article(member,dep,des,date,time,current,title,content);
        }


    }


}
