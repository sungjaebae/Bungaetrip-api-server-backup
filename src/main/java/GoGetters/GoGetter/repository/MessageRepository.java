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

    //받는 사람 아이디를 기준으로 쪽지를 가져오기
    public List<Message> findMessagesByReceiverId(Long receiverId){
        String query="select m from Message m " +
                "join fetch m.receiver r " +
                "join fetch r.member rm where rm.id=:memberId order by m.created desc";
        return em.createQuery(query,Message.class)
                .setParameter("memberId",receiverId).getResultList();
    }

    public List<Message> findAllMessages() {
        return em.createQuery("select m from Message m").getResultList();
    }

    public List<Message> findMessagesBySenderAndReceiverId(Long senderId, Long receiverId) {
        String query="select m from Message m " +
                "join fetch m.sender s " +
                "join fetch s.member sm "+
                "join fetch m.receiver r " +
                "join fetch r.member rm "+
                "where sm.id=:senderId and rm.id=:receiverId";
        return em.createQuery(query).setParameter("senderId", senderId)
                .setParameter("receiverId", receiverId)
                .getResultList();
    }
}
