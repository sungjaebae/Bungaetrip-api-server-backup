package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.Receiver;
import GoGetters.GoGetter.domain.Sender;
import GoGetters.GoGetter.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    //회원 생성
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long memberId){
        return em.find(Member.class,memberId);
    }

    public List<Member> findAllUsers(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findUserByEmail(String email) {
        String query="select m from Member m where m.email=:email";
        return em.createQuery(query, Member.class).setParameter("email",email).getResultList();
    }

    public List<Member> loadUserbyUsername(String username) {
        return em.createQuery("select m from Member m where m.username=:username",Member.class)
                .setParameter("username", username).getResultList();
    }

    public Sender findSender(Long memberId) {
        String query="select s from Sender s join fetch s.member m where m.id=:memberId";
        return em.createQuery(query,Sender.class)
                .setParameter("memberId",memberId)
                .getSingleResult();
    }

    public Receiver findReceiver(Long memberId) {
        String query="select r from Receiver r join fetch r.member m where m.id=:memberId";
        return em.createQuery(query,Receiver.class)
                .setParameter("memberId",memberId)
                .getSingleResult();
    }

    public List<Member> findMembersByUsername(String username) {
        String query = "select m from Member m where m.username=:username";
        return em.createQuery(query, Member.class).setParameter("username", username).getResultList();
    }
}
