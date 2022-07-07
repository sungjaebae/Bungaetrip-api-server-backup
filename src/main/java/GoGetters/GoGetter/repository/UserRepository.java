package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.Receiver;
import GoGetters.GoGetter.domain.Sender;
import GoGetters.GoGetter.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    //회원 생성
    public Long save(User user){
        em.persist(user);
        return user.getId();
    }

    public User findUser(Long user_id){
        return em.find(User.class,user_id); 
    }

    public List<User> findAllUsers(){
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public List<User> findUserByEmail(String email) {
        String query="select u from User u where u.email=:email";
        return em.createQuery(query,User.class).setParameter("email",email).getResultList();
    }

    public Sender findSender(Long userId) {
        String query="select s from Sender s join fetch s.user u where u.id=:userId";
        return em.createQuery(query,Sender.class)
                .setParameter("userId",userId)
                .getSingleResult();
    }

    public Receiver findReceiver(Long userId) {
        String query="select r from Receiver r join fetch r.user u where u.id=:userId";
        return em.createQuery(query,Receiver.class)
                .setParameter("userId",userId)
                .getSingleResult();
    }

}
