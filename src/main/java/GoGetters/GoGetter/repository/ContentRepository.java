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
        String likeVariable="'%"+searchKeyword+"%'";

        String query = "select c from Content c" +
                " join fetch c.images" +
                " where c.title like "+likeVariable;
        return em.createQuery(query,Content.class)
                .getResultList();
    }

    public List<Content> findAllByLocationAndFilter(Double left, Double right, Double top, Double bottom,
                                                    ContentType filter,Integer count) {
        String query = "select c from Content c" +
                " join fetch c.images"+
                " where c.latitude <= :top" +
                " and c.latitude >= :bottom" +
                " and c.longitude <= :right" +
                " and c.longitude >= :left";

        if (filter != null) {
            query+= " and c.contentType=:filter";
        }
        query+= " order by (c.rating*c.visitorReview*c.blogReview) desc";

        TypedQuery<Content> contentTypedQuery = em.createQuery(query, Content.class)
                .setParameter("left", left)
                .setParameter("right", right)
                .setParameter("top", top)
                .setParameter("bottom", bottom)
                .setMaxResults(count);

        if (filter != null) {
            contentTypedQuery.setParameter("filter",filter);
        }
//        contentTypedQuery
//                .setParameter("multiple", multiple);
//                .setParameter("limitCount", limitCount);
        return contentTypedQuery.getResultList();

    }

    public List<Content> findRestaurantsPeopleLike(Integer count) {
        String query="select c from Content c" +
                " join fetch c.images"+
                " where c.contentType=:restaurant" +
                " order by c.rating*c.visitorReview*c.blogReview desc";
        return em.createQuery(query, Content.class)
                .setParameter("restaurant", ContentType.RESTAURANT)
                .setMaxResults(count)
                .getResultList();
    }

    public List<Content> findCafesPeopleLike(Integer count) {
        String query="select c from Content c" +
                " join fetch c.images"+
                " where c.contentType=:cafe" +
                " order by c.rating*c.visitorReview*c.blogReview desc";
        return em.createQuery(query, Content.class)
                .setParameter("cafe", ContentType.CAFE)
                .setMaxResults(count)
                .getResultList();
    }

    public List<Content> findAttractionsPeopleLike(Integer count) {
        String query="select c from Content c" +
                " join fetch c.images"+
                " where c.contentType=:attraction" +
                " order by c.rating*c.visitorReview*c.blogReview desc";
        return em.createQuery(query, Content.class)
                .setParameter("attraction", ContentType.ATTRACTION)
                .setMaxResults(count)
                .getResultList();
    }
}
