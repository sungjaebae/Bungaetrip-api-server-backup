package GoGetters.GoGetter.repository.query;

import GoGetters.GoGetter.domain.content.ContentType;
import GoGetters.GoGetter.dto.content.ContentQueryResponse;
import GoGetters.GoGetter.dto.content.ContentResponse;
import GoGetters.GoGetter.dto.image.ContentImageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ContentQueryRepository {
    @PersistenceContext
    private final EntityManager em;

    public List<ContentResponse> findBestPlaceByDistance(Double memberLatitude, Double memberLongitude,
                                                         Integer offset, Integer limit,
                                                         ContentType contentType, Double limitDistance) {
        List<ContentQueryResponse> result = findBestContentList(memberLatitude,memberLongitude,offset,limit
                ,contentType,limitDistance);
        log.info("print: {}",result.size());
//        Map<Long,List<ContentImageDto>> imageListMap=findContentImageMap(
//                toContentIds(result)
//        );
//        result.forEach(c-> c.setImageList(imageListMap.get(c.getContentId())));

        return new ArrayList<>();
    }

    private List<ContentQueryResponse> findBestContentList(Double memberLatitude,Double memberLongitude,
                                                                Integer offset,Integer limit,ContentType contentType,
                                                           Double limitDistance
                                                           ) {

//        String query="select c from Content c" +
//                " where ST_Distance_Sphere(point(c.longitude,c.latitude), point(:x,:y)) < 5000" +
//                " order by ST_Distance_Sphere(point(c.longitude,c.latitude), point(:x,:y))";
//        return em.createQuery(query, Content.class)
//                .setParameter("x",memberLongitude)
//                .setParameter("y",memberLatitude)
//                .getResultList();
        String query="select"+
                " ST_Distance_Sphere(point(c.longitude,c.latitude), point(:x,:y))" +
                " from Content c" +
                " where ST_Distance_Sphere(point(c.longitude,c.latitude), point(:x,:y)) < 5000" +
                " order by c.rating*c.visitorReview*c.blogReview desc";
        try {
            return em.createQuery(query, ContentQueryResponse.class)
                    .setParameter("x",memberLongitude)
                    .setParameter("y",memberLatitude)
                    .getResultList();
        } catch (Exception e) {
            log.info("print : {}", e.getMessage());
        }

////        return em.createQuery(
//                        "select new GoGetters.GoGetter.dto.content.ContentQueryResponse( c.id, c.title, c.subtitle ," +
//                                " c.contentType, c.rating, c.blogReview, c.visitorReview) " +
//                                " from Content c" +
//                                " where c.contentType=:contentType" +
//                                " and (6371*acos(cos(radians(c.latitude))*cos(radians(:latitude))*cos(radians(:longitude) \"\n" +
//                                "                + \"-radians(c.longitude))+sin(radians(c.latitude))*sin(radians(:latitude))))" +
//                                "< : limitDistance" +
////                                " and ST_Distance_Sphere(point(c.longitude,c.latitude), point(:x,:y)) < 5000" +
//                                " order by c.rating*c.visitorReview*c.blogReview desc", ContentQueryResponse.class)
//                .setParameter("contentType", contentType)
//                .setParameter("longitude",memberLongitude)
//                .setParameter("latitude", memberLatitude)
//                .setParameter("limitDistance",limitDistance)
//                .setFirstResult(offset)
//                .setMaxResults(limit)
//                .getResultList();
//        "(6371*acos(cos(radians(:memberLatitude))*cos(radians(c.latitude))*cos(radians(c.longitude)-" +
//                "radians(:memberLongitude))" +
//                "+sin(radians(:memberLatitude))*sin(radians(c.latitude)))) as distance)" +
        return null;
    }
    private List<Long> toContentIds(List<ContentQueryResponse> result) {
        return result.stream().map(contentQueryResponse -> contentQueryResponse.getContentId())
                .collect(Collectors.toList());
    }

    private Map<Long, List<ContentImageDto>> findContentImageMap(List<Long> contentIds) {
        List<ContentImageDto> contentImages = em.createQuery(
                        "select new GoGetters.GoGetter.dto.image.ContentImageDto(i.content.id," +
                                " i.id, i.imageUrl)" +
                                " from Image i" +
                                " where i.content.id in :contentIds", ContentImageDto.class)
                .setParameter("contentIds", contentIds)
                .getResultList();

        return contentImages.stream()
                .collect(Collectors.groupingBy(ContentImageDto::getContentId));
    }
}
