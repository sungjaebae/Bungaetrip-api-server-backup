package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.domain.content.ContentType;
import GoGetters.GoGetter.dto.content.ContentListPeopleLikeResponse;
import GoGetters.GoGetter.dto.content.ContentQueryResponse;
import GoGetters.GoGetter.dto.content.ContentResponse;
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
                                           @RequestParam(value = "filter",required = false) ContentType filter,
                                           @RequestParam(value = "offset",
                                                   defaultValue = "0") Integer offset,
                                           @RequestParam(value = "limit", defaultValue
                                                   = "100") Integer limit
    ) {
        Integer count=50;
        List<Content> contentList = contentService.findPlaceInAreaByFilter(left, right, top, bottom,
                filter,offset,limit);
        List<ContentResponse> collect = contentList.stream().map(content -> new ContentResponse(content))
                .collect(Collectors.toList());
        return ResponseUtil.successResponse(HttpStatus.OK, collect);
    }

    @GetMapping(value = "/{contentId}")
    public ResponseEntity readTourContent(@PathVariable(value = "contentId") Long contentId
            ,@RequestParam(value = "offset",
            defaultValue = "0") Integer offset,
                                          @RequestParam(value = "limit", defaultValue
                                                  = "100") Integer limit) {
        Content content = contentService.findContentWithArticles(contentId,offset,limit);
        return ResponseUtil.successResponse(HttpStatus.OK, new ContentWithArticlesResponse(content));
    }

    @GetMapping(value = "/recommend")
    public ResponseEntity listRecommendContent(@RequestParam(value = "currentLatitude")Double currentLatitude,
                                               @RequestParam(value = "currentLongitude")Double currentLongitude,
                                               @RequestParam(value = "offset",
                                                       defaultValue = "0") Integer offset,
                                               @RequestParam(value = "limit", defaultValue
                                                       = "10") Integer limit) {
        List<ContentQueryResponse> bestRestaurants=contentService
                .findBestContents(currentLatitude,currentLongitude,offset,limit,ContentType.RESTAURANT,5.0);
        List<ContentQueryResponse> bestCafes = contentService
                .findBestContents(currentLatitude, currentLongitude, offset, limit, ContentType.CAFE, 5.0);
        List<ContentQueryResponse> bestAttractions = contentService
                .findBestContents(currentLatitude, currentLongitude, offset, limit, ContentType.ATTRACTION, 10.0);

        List<ContentListPeopleLikeResponse> contentsPeopleLike=new ArrayList<>();
        contentsPeopleLike.add(new ContentListPeopleLikeResponse("주변에 가장 인기 있는 맛집",
                bestRestaurants));
        contentsPeopleLike.add(new ContentListPeopleLikeResponse("주변에 가장 인기 있는 카페",
                bestCafes));
        contentsPeopleLike.add(new ContentListPeopleLikeResponse("주변에 가장 인기 있는 관광지",
                bestAttractions));

        return ResponseUtil.successResponse(HttpStatus.OK,"");
    }

    @GetMapping(value = "", params = "searchKeyword")
    public ResponseEntity listTourContentBySearchKeyword(@RequestParam(value = "searchKeyword") String searchKeyword,
                                                         @RequestParam(value = "offset",
                                                                 defaultValue = "0") Integer offset,
                                                         @RequestParam(value = "limit", defaultValue
                                                                 = "100") Integer limit) {
        List<Content> contentList = contentService.findAllBySearchKeyword(searchKeyword,offset,limit);
        List<ContentResponse> collect = contentList.stream()
                .map(content -> new ContentResponse(content)).collect(Collectors.toList());
        return ResponseUtil.successResponse(HttpStatus.OK, collect);
    }
}
