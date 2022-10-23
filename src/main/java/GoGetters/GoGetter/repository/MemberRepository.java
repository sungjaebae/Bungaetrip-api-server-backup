package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.MessageResource;
import GoGetters.GoGetter.domain.message.Receiver;
import GoGetters.GoGetter.domain.message.Sender;
import GoGetters.GoGetter.domain.member.Member;
import GoGetters.GoGetter.dto.member.UpdateMemberRequest;
import GoGetters.GoGetter.exception.Member.NoSuchMemberException;
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
        String query = "select m from Member m " +
                "where m.id=:memberId " +
                "and m.deletedAt is null";

        List<Member> member = em.createQuery(query, Member.class)
                .setParameter("memberId", memberId)
                .getResultList();
        if (member.isEmpty()) {
            throw new NoSuchMemberException(MessageResource.memberNotExist);
        }
        return member.get(0);
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

    public List<Sender> findSender(Long memberId) {
        String query="select s from Sender s " +
                "join fetch s.member m " +
                "where m.id=:memberId " +
                "and m.deletedAt is null";
        return em.createQuery(query,Sender.class)
                .setParameter("memberId",memberId)
                .getResultList();
    }

    public List<Receiver> findReceiver(Long memberId) {
        String query="select r from Receiver r " +
                "join fetch r.member m " +
                "where m.id=:memberId " +
                "and m.deletedAt is null";
        return em.createQuery(query,Receiver.class)
                .setParameter("memberId",memberId)
                .getResultList();
    }

    public List<Member> findMembersByUsername(String username) {
        String query = "select m from Member m " +
                "where m.username=:username " +
                "and m.deletedAt is null";
        return em.createQuery(query, Member.class).setParameter("username", username).getResultList();
    }

    public Long updateMemberInfo(Member member, UpdateMemberRequest memberInfoDto) {
        member.updateMyInfo(memberInfoDto);
        return member.getId();
    }


    public Long updateMemberInfo(Member member, UpdateMemberRequest memberInfoDto, String profileUrl, String profileName) {
        member.updateMyInfo(memberInfoDto,profileUrl,profileName);
        return member.getId();
    }
}
