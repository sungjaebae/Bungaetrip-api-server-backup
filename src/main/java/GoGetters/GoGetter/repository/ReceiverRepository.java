package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.message.Receiver;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ReceiverRepository {
    @PersistenceContext
    private EntityManager em;

    public Long save(Receiver receiver) {
        em.persist(receiver);
        return receiver.getId();
    }

    public Receiver findReceiver(Long receiverId) {
        return em.find(Receiver.class,receiverId);
    }
}
