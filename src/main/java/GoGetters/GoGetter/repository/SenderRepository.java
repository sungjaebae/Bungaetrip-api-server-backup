package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.Receiver;
import GoGetters.GoGetter.domain.Sender;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SenderRepository {
    @PersistenceContext
    private EntityManager em;

    public Long save(Sender sender) {
        em.persist(sender);
        return sender.getId();
    }

    public Sender findSender(Long senderId) {
        return em.find(Sender.class,senderId);
    }
}
