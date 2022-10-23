package GoGetters.GoGetter.service.query;

import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.domain.content.ContentType;
import GoGetters.GoGetter.dto.content.ContentListPeopleLikeResponse;
import GoGetters.GoGetter.dto.content.ContentQueryResponse;
import GoGetters.GoGetter.dto.content.ContentResponse;
import GoGetters.GoGetter.dto.content.ContentWithArticlesResponse;
import GoGetters.GoGetter.repository.ContentRepository;
import GoGetters.GoGetter.repository.query.ContentQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentQueryService {
    private final ContentRepository contentRepository;
    private final ContentQueryRepository contentQueryRepository;

    public List<ContentResponse> listTourContents(Double left, Double right, Double top,
                                                  Double bottom, ContentType contentType,
                                                  Integer offset, Integer limit) {
        List<Content> contentList = contentRepository.findAllByLocationAndFilter(left, right, top, bottom,
                contentType,offset,limit);
        return contentList.stream().map(content -> new ContentResponse(content))
                .collect(Collectors.toList());
    }

    public ContentWithArticlesResponse readContent(Long contentId, Integer offset, Integer limit) {
        Content content = contentRepository.findContentWithArticles(contentId, offset, limit);
        return new ContentWithArticlesResponse(content);
    }

    public List<ContentListPeopleLikeResponse> listRecommendContent(Double currentLatitude, Double currentLongitude,
                                                                    Integer offset, Integer limit) {
        List<ContentQueryResponse> bestRestaurants=contentQueryRepository
                .findBestPlaceByDistance(currentLatitude,currentLongitude,offset,limit,ContentType.RESTAURANT,5.0);
        List<ContentQueryResponse> bestCafes = contentQueryRepository
                .findBestPlaceByDistance(currentLatitude, currentLongitude, offset, limit, ContentType.CAFE, 5.0);
        List<ContentQueryResponse> bestAttractions = contentQueryRepository
                .findBestPlaceByDistance(currentLatitude, currentLongitude, offset, limit, ContentType.ATTRACTION, 10.0);

        List<ContentListPeopleLikeResponse> contentsPeopleLike=new ArrayList<>();
        if (!bestRestaurants.isEmpty()) {
            contentsPeopleLike.add(new ContentListPeopleLikeResponse("주변에 가장 인기 있는 맛집",
                    bestRestaurants));
        }
        if (!bestCafes.isEmpty()) {
            contentsPeopleLike.add(new ContentListPeopleLikeResponse("주변에 가장 인기 있는 카페",
                    bestCafes));
        }
        if (!bestAttractions.isEmpty()) {
            contentsPeopleLike.add(new ContentListPeopleLikeResponse("주변에 가장 인기 있는 관광지",
                    bestAttractions));
        }
        return contentsPeopleLike;
    }

    public List<ContentResponse> listContentBySearchKeyword(String searchKeyword,
                                                            Integer offset, Integer limit) {
        List<Content> contentList = contentRepository.findAllBySearchKeyword(searchKeyword,offset,limit);
        return contentList.stream()
                .map(content -> new ContentResponse(content)).collect(Collectors.toList());
    }
}
