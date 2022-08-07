package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.ReportArticle;
import GoGetters.GoGetter.domain.ReportMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ReportRepository {
    @PersistenceContext
    private EntityManager em;

    public Long saveMember(ReportMember reportMember) {
        em.persist(reportMember);
        return reportMember.getId();
    }

    public Long saveArticle(ReportArticle reportArticle) {
        em.persist(reportArticle);
        return reportArticle.getId();
    }
}
