package GoGetters.GoGetter.repository.query;

import GoGetters.GoGetter.domain.content.ContentType;
import GoGetters.GoGetter.dto.content.ContentQueryResponse;
import GoGetters.GoGetter.dto.image.ContentImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ContentQueryRepository {
    @PersistenceContext
    private final EntityManager em;

    public List<ContentQueryResponse> findBestPlaceByDistance(Double memberLatitude,Double memberLongitude,
                                                                   Integer offset,Integer limit,ContentType contentType) {
        List<ContentQueryResponse> result = findBestContentList(memberLatitude,memberLongitude,offset,limit,contentType);

        Map<Long,List<ContentImageDto>> imageListMap=findContentImageMap(
                toContentIds(result)
        );
        result.forEach(c-> c.setImageList(imageListMap.get(c.getContentId())));

        return result;
    }
    private List<ContentQueryResponse> findBestContentList(Double memberLatitude,Double memberLongitude,
                                                                Integer offset,Integer limit,ContentType contentType
                                                           ) {

        return em.createQuery(
                        "select new GoGetters.GoGetter.dto.content.ContentQueryResponse( c.id, c.title, c.subtitle ," +
                                " c.contentType, c.rating, c.blogReview, c.visitorReview)" +
                        " from Content c" +
                                " where c.contentType=:contentType" +
                                " order by c.rating*c.visitorReview*c.blogReview desc" ,ContentQueryResponse.class)
                .setParameter("contentType", contentType)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
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
