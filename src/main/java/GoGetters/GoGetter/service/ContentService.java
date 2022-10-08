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

    public List<Content> findAllBySearchKeyword(String searchKeyword) {
        return contentRepository.findAllBySearchKeyword(searchKeyword);
    }

    public List<Content> findPlaceInAreaByFilter(Double left, Double right, Double top, Double bottom,
                                                 ContentType filter,Integer count) {
        return contentRepository.findAllByLocationAndFilter(left, right, top, bottom, filter,count);
    }

    public List<Content> findRestaurantsPeopleLike(Double memberLatitude, Double memberLongitude,Integer count) {
        return contentRepository.findRestaurantsPeopleLike(memberLatitude,memberLongitude,count);
    }

    public List<Content> findCafesPeopleLike(Double memberLatitude, Double memberLongitude,Integer count) {
        return contentRepository.findCafesPeopleLike(memberLatitude,memberLongitude,count);

    }

    public List<Content> findAttractionsPeopleLike(Double memberLatitude, Double memberLongitude,Integer count) {
        return contentRepository.findAttractionsPeopleLike(memberLatitude,memberLongitude,count);

    }
}
