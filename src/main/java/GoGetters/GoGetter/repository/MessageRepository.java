package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MessageRepository {
    @PersistenceContext
    private EntityManager em;

    //메시지 작성
    public Long save(Message message) {
        em.persist(message);
        return message.getId();
    }

    //메시지 가져오기
    public Message findMessage(Long message_id){
        return em.find(Message.class, message_id);
    }




}
