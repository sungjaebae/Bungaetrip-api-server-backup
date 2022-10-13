package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.domain.content.ContentType;
import GoGetters.GoGetter.dto.content.ContentQueryResponse;
import GoGetters.GoGetter.repository.ContentRepository;
import GoGetters.GoGetter.repository.query.ContentQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentService {
    private final ContentRepository contentRepository;
    private final ContentQueryRepository contentQueryRepository;

    public Content findOne(Long contentId) {
        return contentRepository.findOne(contentId);
    }


    public Content findContentWithArticles(Long contentId,Integer offset,Integer limit) {
        System.out.println("111111");
        return contentRepository.findContentWithArticles(contentId,offset,limit);
    }


    public List<Content> findAllBySearchKeyword(String searchKeyword,Integer offset,Integer limit) {
        return contentRepository.findAllBySearchKeyword(searchKeyword,offset,limit);
    }

    public List<Content> findPlaceInAreaByFilter(Double left, Double right, Double top, Double bottom,
                                                 ContentType filter,Integer offset,Integer limit) {
        return contentRepository.findAllByLocationAndFilter(left, right, top, bottom, filter,offset,limit);
    }

    public List<ContentQueryResponse> findBestContents(Double memberLatitude, Double memberLongitude,
                                                       Integer offset, Integer limit,
                                                       ContentType contentType, Double limitDistance) {
        return   contentQueryRepository.findBestPlaceByDistance(memberLatitude, memberLongitude, offset, limit,
                contentType, limitDistance);
    }

    public List<Content> findCafesPeopleLike(Double memberLatitude, Double memberLongitude,
                                             Integer offset,Integer limit) {
        return contentRepository.findCafesPeopleLike(memberLatitude,memberLongitude,offset,limit);

    }

    public List<Content> findAttractionsPeopleLike(Double memberLatitude, Double memberLongitude,
                                                   Integer offset,Integer limit) {
        return contentRepository.findAttractionsPeopleLike(memberLatitude,memberLongitude,offset,limit);

    }
}
