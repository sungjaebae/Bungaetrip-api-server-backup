package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.domain.content.ContentType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Getter
@Slf4j
public class ContentRepository {
    @PersistenceContext
    private EntityManager em;

    private Long save(Content content) {
        em.persist(content);
        return content.getId();
    }

    public Content findContentWithArticles(Long contentId) {
        String query = "select distinct c from Content c" +
//                " join fetch c.images" +
//                " left join Article a on a.content = c" +
//                " left join Member m on a.writer = m" +
                " join fetch c.articles a" +
                " join fetch a.writer m"+
                " where c.id=:contentId";
        Content content = null;
        try {
            System.out.println("211111");
            content = em.createQuery(query, Content.class).setParameter("contentId", contentId).getSingleResult();
        } catch (EmptyResultDataAccessException | NoResultException e) {
            content=em.createQuery("select distinct c from Content c" +
                            " where c.id=:contentId",Content.class)
                    .setParameter("contentId", contentId)
                    .getSingleResult();
        } catch(Exception e) {
            log.info("print except: {}",e.toString());
            return null;
        }
        return content;

    }

    public Content findOne(Long contentId) {
        return em.find(Content.class, contentId);
    }

    public List<Content> findAllBySearchKeyword(String searchKeyword) {
        String likeVariable="'%"+searchKeyword+"%'";

        String query = "select distinct c from Content c" +
//                " join fetch c.articles"+
                " join fetch c.images" +
                " where c.title like "+likeVariable;
        return em.createQuery(query,Content.class)
                .getResultList();
    }

    public List<Content> findAllByLocationAndFilter(Double left, Double right, Double top, Double bottom,
                                                    ContentType filter,Integer count) {
        System.out.println("content log");
        String query = "select distinct c from Content c" +
//                " join fetch c.images"+
                " left join c.articles a" +
                " left join a.writer"+
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

    public List<Content> findRestaurantsPeopleLike(Double memberLatitude,Double memberLongitude,Integer count) {
        String query="select distinct c from Content c" +
                " join fetch c.images"+
//                " join fetch c.articles"+
                " where c.contentType=:restaurant" +
                " order by c.rating*c.visitorReview*c.blogReview desc";
        return em.createQuery(query, Content.class)
                .setParameter("restaurant", ContentType.RESTAURANT)
                .setMaxResults(count)
                .getResultList();
    }

    public List<Content> findCafesPeopleLike(Double memberLatitude,Double memberLongitude,Integer count) {
        String query="select distinct c from Content c" +
                " join fetch c.images"+
//                " join fetch c.articles"+
                " where c.contentType=:cafe" +
                " order by c.rating*c.visitorReview*c.blogReview desc";
        return em.createQuery(query, Content.class)
                .setParameter("cafe", ContentType.CAFE)
                .setMaxResults(count)
                .getResultList();
    }

    public List<Content> findAttractionsPeopleLike(Double memberLatitude,Double memberLongitude,Integer count) {
        String query="select distinct c from Content c" +
                " join fetch c.images"+
//                " join fetch c.articles"+
                " where c.contentType=:attraction" +
                " order by c.rating*c.visitorReview*c.blogReview desc";
        return em.createQuery(query, Content.class)
                .setParameter("attraction", ContentType.ATTRACTION)
                .setMaxResults(count)
                .getResultList();
    }

    private String contentBelowCertainDistanceQuery() {
        return "select c from Content c" +
                " as distance" +
                "";
//            "SELECT\n" +
//                    "\n" +
//                    "    (6371*acos(cos(radians(userLatitude))*cos(radians(cafeLatitude))*cos(radians(cafeHardness)\n" +
//                    "\n" +
//                    "    -radians(userHardness))+sin(radians(userLatitude))*sin(radians(cafeLatitude))))\n" +
//                    "\n" +
//                    "    AS distance\n" +
//                    "\n" +
//                    "FROM Cafe,User\n" +
//                    "\n" +
//                    "-- HAVING distance <= (이 값을 지정하면 특정거리 이하만 출력하게 할 수 있음)\n" +
//                    "\n" +
//                    "ORDER BY distance;\n" +
//                    "출처: https://yusang.tistory.com/48 [YS's develop story:티스토리]"
    }
}
