package GoGetters.GoGetter.repository.query;

import GoGetters.GoGetter.domain.content.ContentType;
import GoGetters.GoGetter.dto.content.ContentQueryResponse;
import GoGetters.GoGetter.dto.image.ContentImageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public List<ContentQueryResponse> findBestPlaceByDistance(Double memberLatitude, Double memberLongitude,
                                                         Integer offset, Integer limit,
                                                         ContentType contentType, Double limitDistance) {
        List<ContentQueryResponse> result = findBestContentList(memberLatitude,memberLongitude,offset,limit
                ,contentType,limitDistance);
        log.info("print size: {}",result.size());


        return result;
    }

    private List<ContentQueryResponse> findBestContentList(Double memberLatitude,Double memberLongitude,
                                                                Integer offset,Integer limit,ContentType contentType,
                                                           Double limitDistance
                                                           ) {
        List<ContentQueryResponse> contents = findContents(memberLatitude,memberLongitude,offset,limit,contentType,limitDistance);
        Map<Long, List<ContentImageDto>> contentImageMap = findContentImageMap(toContentIds(contents));

        //
        contents.forEach(c->c.setImageList(contentImageMap.get(c.getContentId())));

        return contents;
    }

    private List<ContentQueryResponse> findContents(Double memberLatitude,Double memberLongitude,
                                                   Integer offset,Integer limit ,
                                                    ContentType contentType,Double limitDistance) {
        Query query = em.createNativeQuery("SELECT c.content_id,c.title, c.subtitle, c.content_type," +
                        " c.rating, c.blog_review, c.visitor_review," +
                        " ST_Distance_Sphere(point(c.longitude,c.latitude), point(:x,:y))/1000 from content c" +
                        " where c.content_type=:contentType" +
                        " and ST_Distance_Sphere(point(c.longitude,c.latitude), point(:x,:y))/1000 <= :limitDistance" +
                        " order by c.rating*c.visitor_review*c.blog_review desc limit :limit")
                .setParameter("x", memberLongitude)
                .setParameter("y", memberLatitude)
                .setParameter("contentType",contentType.toString())
                .setParameter("limitDistance", limitDistance)
                .setParameter("limit",limit);
        List<Object[]> resultList = query.getResultList();
        List<ContentQueryResponse> contentQueryResponseList = new ArrayList<>();
        for (Object[] row : resultList) {
            contentQueryResponseList.add(new ContentQueryResponse(row[0].toString(), row[1].toString(),
                    row[2].toString(), row[3].toString(), row[4].toString(), row[5].toString(),
                    row[6].toString(), row[7].toString()));
            log.info("print id : "+row[0]);
            log.info("print title : "+row[1]);
            log.info("print subtitle: "+row[2]);
            log.info("print contentType: "+row[3]);
            log.info("print rating: "+row[4]);
            log.info("print blog: "+row[5]);
            log.info("print visitor: "+row[6]);
            log.info("print distance: "+row[7]);
        }

        return contentQueryResponseList;
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
