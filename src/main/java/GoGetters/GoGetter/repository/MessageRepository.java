package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.message.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MessageRepository {
    @PersistenceContext
    private EntityManager em;

    //메세지 작성
    public Long save(Message message) {
        em.persist(message);
        return message.getId();
    }

    //메세지 가져오기
    public List<Message> findMessage(Long messageId){
        String query = "select m from Message m " +
                "join fetch m.sender s " +
                "join fetch s.member sm "+
                "join fetch m.receiver r " +
                "join fetch r.member rm " +
                "where m.id=:messageId " +
                "and sm.deletedAt is null " +
                "and rm.deletedAt is null";
        return em.createQuery(query, Message.class)
                .setParameter("messageId", messageId)
                .getResultList();
    }

    //받는 사람 아이디를 기준으로 쪽지를 가져오기
    public List<Message> findMessagesByReceiverId(Long receiverId){
        String query="select m from Message m " +
                "join fetch m.receiver r " +
                "join fetch r.member rm " +
                "where rm.id=:memberId " +
                "and rm.deletedAt is null " +
                "order by m.createdAt desc";
        return em.createQuery(query,Message.class)
                .setParameter("memberId",receiverId).getResultList();
    }


    public List<Message> findSentMessagesByMemberId(Long memberId) {
        String query = "select m from Message m " +
                "join fetch m.sender s " +
                "join fetch s.member sm " +
                "where sm.id=:memberId " +
                "and sm.deletedAt is null " +
                "order by m.createdAt desc";
        return em.createQuery(query, Message.class)
                .setParameter("memberId",memberId).getResultList();
    }

}
