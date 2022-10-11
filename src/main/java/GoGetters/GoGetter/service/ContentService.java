package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.domain.content.ContentType;
import GoGetters.GoGetter.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;

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

    public List<Content> findRestaurantsPeopleLike(Double memberLatitude, Double memberLongitude,
                                                   Integer offset,Integer limit) {
        return contentRepository.findRestaurantsPeopleLike(memberLatitude,memberLongitude,offset,limit);
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
