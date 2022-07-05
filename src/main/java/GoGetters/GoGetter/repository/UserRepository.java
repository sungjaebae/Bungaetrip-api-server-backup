package GoGetters.GoGetter.repository;

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
}
