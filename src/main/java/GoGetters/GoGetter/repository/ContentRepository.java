package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.domain.content.ContentType;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Getter
public class ContentRepository {
    @PersistenceContext
    private EntityManager em;

    private Long save(Content content) {
        em.persist(content);
        return content.getId();
    }

    public Content findOne(Long contentId) {
        return em.find(Content.class, contentId);
    }

    public List<Content> findAllBySearchKeyword(String searchKeyword) {
        String query = "select c from Content" +
                " where c.title=:searchKeyword";
        return em.createQuery(query,Content.class)
                .setParameter("searchKeyword",searchKeyword)
                .getResultList();
    }

    public List<Content> findAllByLocationAndFilter(Double left, Double right, Double top, Double bottom,
                                                    ContentType filter) {
        Long multiple= 2L;
        Integer limitCount=50;
        String query = "select c from Content c" +
                " where c.latitude <= :top" +
                " and c.latitude >= :bottom" +
                " and c.longitude <= :right" +
                " and c.longitude >= :left";

        if (filter != null) {
            query+= " and c.contentType=:filter";
        }
        query+= " order by (c.rating*c.ratingCount*c.review) desc";

        TypedQuery<Content> contentTypedQuery = em.createQuery(query, Content.class)
                .setParameter("left", left)
                .setParameter("right", right)
                .setParameter("top", top)
                .setParameter("bottom", bottom)
                .setMaxResults(limitCount);

        if (filter != null) {
            contentTypedQuery.setParameter("filter",filter);
        }
//        contentTypedQuery
//                .setParameter("multiple", multiple);
//                .setParameter("limitCount", limitCount);
        return contentTypedQuery.getResultList();

    }
}
