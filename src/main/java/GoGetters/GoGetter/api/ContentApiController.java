package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.domain.content.ContentType;
import GoGetters.GoGetter.dto.content.ContentListPeopleLikeResponse;
import GoGetters.GoGetter.dto.content.ContentListResponse;
import GoGetters.GoGetter.dto.content.ContentWithArticlesResponse;
import GoGetters.GoGetter.service.ArticleService;
import GoGetters.GoGetter.service.ContentService;
import GoGetters.GoGetter.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "contents")
@Slf4j
public class ContentApiController {
    private final ContentService contentService;
    private final ArticleService articleService;

    @GetMapping(value = "",params = {"left","right","top","bottom"})
    public ResponseEntity listTourContents(@RequestParam(value = "left") Double left,
                                           @RequestParam(value = "right") Double right,
                                           @RequestParam(value = "top") Double top,
                                           @RequestParam(value = "bottom") Double bottom,
                                           @RequestParam(value = "filter",required = false) ContentType filter
    ) {
        Integer count=50;
        List<Content> contentList = contentService.findPlaceInAreaByFilter(left, right, top, bottom,
                filter,count);
        List<ContentListResponse> collect = contentList.stream().map(content -> new ContentListResponse(content))
                .collect(Collectors.toList());
        return ResponseUtil.successResponse(HttpStatus.OK, collect);
    }

    @GetMapping(value = "/{contentId}")
    public ResponseEntity readTourContent(@PathVariable(value = "contentId") Long contentId) {
        Content content = contentService.findOne(contentId);
        log.info("articles size: {}",content.getArticles().size());
        return ResponseUtil.successResponse(HttpStatus.OK, new ContentWithArticlesResponse(content));
    }

    @GetMapping(value = "/recommend")
    public ResponseEntity listRecommendContent(@RequestParam(value = "currentLatitude")Double currentLatitude,
                                               @RequestParam(value = "currentLongitude")Double currentLongitude) {
        Integer count=10;
        List<Content> restaurantListPeopleLike=contentService
                .findRestaurantsPeopleLike(currentLatitude,currentLongitude,count);
        List<Content> cafeListPeopleLike=contentService
                .findCafesPeopleLike(currentLatitude,currentLongitude,count);
        List<Content> attractionListPeopleLike=contentService
                .findAttractionsPeopleLike(currentLatitude,currentLongitude,count);

        List<ContentListPeopleLikeResponse> contentsPeopleLike=new ArrayList<>();
        contentsPeopleLike.add(new ContentListPeopleLikeResponse("주변에 가장 인기 있는 맛집",
                restaurantListPeopleLike));
        contentsPeopleLike.add(new ContentListPeopleLikeResponse("주변에 가장 인기 있는 카페"
                ,cafeListPeopleLike));
        contentsPeopleLike.add(new ContentListPeopleLikeResponse("주변에 가장 인기 있는 관광지",
                attractionListPeopleLike));

        return ResponseUtil.successResponse(HttpStatus.OK,contentsPeopleLike);
    }

    @GetMapping(value = "",params = "searchKeyword")
    public ResponseEntity listTourContentBySearchKeyword(@RequestParam(value = "searchKeyword") String searchKeyword) {
        List<Content> contentList=contentService.findAllBySearchKeyword(searchKeyword);
        List<ContentListResponse> collect = contentList.stream()
                .map(content -> new ContentListResponse(content)).collect(Collectors.toList());
        return ResponseUtil.successResponse(HttpStatus.OK, collect);
    }
}
