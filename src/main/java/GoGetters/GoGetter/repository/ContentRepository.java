package GoGetters.GoGetter.repository;

import GoGetters.GoGetter.MessageResource;
import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.domain.content.ContentType;
import GoGetters.GoGetter.exception.Content.NoSuchContentException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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

    public Content findContentWithArticles(Long contentId,Integer offset,Integer limit) {
        String query = "select distinct c from Content c" +
                " where c.id=:contentId";

        return em.createQuery(query, Content.class)
                .setParameter("contentId", contentId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList().get(0);

    }

    public Content findOne(Long contentId) {
        List<Content> contents = em.createQuery("select c from Content c" +
                        " where c.id=:contentId", Content.class)
                .setParameter("contentId", contentId)
                .getResultList();
        if (contents.isEmpty()) {
            throw new NoSuchContentException(MessageResource.contentNotExist);
        }
        return contents.get(0);
    }

    public List<Content> findAllBySearchKeyword(String searchKeyword,Integer offset,Integer limit) {
        String likeVariable="'%"+searchKeyword+"%'";

        String query = "select distinct c from Content c" +
                " where c.title like "+likeVariable;
        return em.createQuery(query,Content.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Content> findAllByLocationAndFilter(Double left, Double right, Double top, Double bottom,
                                                    ContentType filter,Integer offset,Integer limit) {
        String query = "select distinct c from Content c" +
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
                .setFirstResult(offset)
                .setMaxResults(limit);

        if (filter != null) {
            contentTypedQuery.setParameter("filter",filter);
        }
//        contentTypedQuery
//                .setParameter("multiple", multiple);
//                .setParameter("limitCount", limitCount);
        return contentTypedQuery.getResultList();

    }

    public List<Content> findRestaurantsPeopleLike(Double memberLatitude,Double memberLongitude
                            ,Integer offset,Integer limit) {
        String query="select distinct c from Content c" +
                " where c.contentType=:restaurant" +
                " order by c.rating*c.visitorReview*c.blogReview desc";
        return em.createQuery(query, Content.class)
                .setParameter("restaurant", ContentType.RESTAURANT)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Content> findCafesPeopleLike(Double memberLatitude,Double memberLongitude,
                                             Integer offset,Integer limit) {
        String query="select distinct c from Content c" +
                " where c.contentType=:cafe" +
                " order by c.rating*c.visitorReview*c.blogReview desc";
        return em.createQuery(query, Content.class)
                .setParameter("cafe", ContentType.CAFE)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Content> findAttractionsPeopleLike(Double memberLatitude,Double memberLongitude,
                                                   Integer offset,Integer limit ) {
        String query="select distinct c from Content c" +
                " where c.contentType=:attraction" +
                " order by c.rating*c.visitorReview*c.blogReview desc";
        return em.createQuery(query, Content.class)
                .setParameter("attraction", ContentType.ATTRACTION)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    private String contentBelowCertainDistanceQuery() {
        return "select c from Content c" +
                " as distance" +
                "";
//            "SELECT\n" +
//                    "\n" +
//                    "    (6371*acos(cos(radians(userLatitude))*cos(radians(cafeLatitude))*cos(radians(cafeHardness)\n" +
//                    "\n" +Ca
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
