package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.Member;
import GoGetters.GoGetter.domain.ReportedArticle;
import GoGetters.GoGetter.domain.ReportedMember;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ReportRepository {
    @PersistenceContext
    private EntityManager em;

    public Long saveMember(ReportedMember reportedMember) {
        em.persist(reportedMember);
        return reportedMember.getId();
    }

    public Long saveArticle(ReportedArticle reportedArticle) {
        em.persist(reportedArticle);
        return reportedArticle.getId();
    }

    public List<ReportedMember> findReportMember(Long reportedMemberId, Long reporterId) {
        String query="select rm from ReportedMember rm " +
                "where rm.reportedMemberId=:reportedMemberId "+
                "and rm.reporter.id=:reporterId";
        return em.createQuery(query,ReportedMember.class)
                .setParameter("reportedMemberId",reportedMemberId)
//                .setParameter("reporterId",reporterId)
                .getResultList();
    }


}
