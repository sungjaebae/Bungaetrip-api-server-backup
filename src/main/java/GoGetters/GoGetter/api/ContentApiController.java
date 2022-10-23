package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.content.ContentType;
import GoGetters.GoGetter.dto.content.ContentListPeopleLikeResponse;
import GoGetters.GoGetter.dto.content.ContentResponse;
import GoGetters.GoGetter.dto.content.ContentWithArticlesResponse;
import GoGetters.GoGetter.service.ContentService;
import GoGetters.GoGetter.service.query.ContentQueryService;
import GoGetters.GoGetter.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "contents")
@Slf4j
public class ContentApiController {
    private final ContentService contentService;
    private final ContentQueryService contentQueryService;

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
        List<ContentResponse> collect = contentQueryService
                .listTourContents(left, right, top, bottom, filter, offset, limit);
        return ResponseUtil.successResponse(HttpStatus.OK, collect);
    }

    @GetMapping(value = "/{contentId}")
    public ResponseEntity readTourContent(@PathVariable(value = "contentId") Long contentId
            ,@RequestParam(value = "offset",
            defaultValue = "0") Integer offset,
                                          @RequestParam(value = "limit", defaultValue
                                                  = "100") Integer limit) {
        ContentWithArticlesResponse contentWithArticlesResponse = contentQueryService.readContent(contentId, offset, limit);
        return ResponseUtil.successResponse(HttpStatus.OK,contentWithArticlesResponse);
    }

    @GetMapping(value = "/recommend")
    public ResponseEntity listRecommendContent(@RequestParam(value = "currentLatitude")Double currentLatitude,
                                               @RequestParam(value = "currentLongitude")Double currentLongitude,
                                               @RequestParam(value = "offset",
                                                       defaultValue = "0") Integer offset,
                                               @RequestParam(value = "limit", defaultValue
                                                       = "10") Integer limit) {
        List<ContentListPeopleLikeResponse> contentsRecommended = contentQueryService
                .listRecommendContent(currentLatitude, currentLongitude, offset, limit);


        return ResponseUtil.successResponse(HttpStatus.OK,contentsRecommended);
    }

    @GetMapping(value = "", params = "searchKeyword")
    public ResponseEntity listTourContentBySearchKeyword(@RequestParam(value = "searchKeyword") String searchKeyword,
                                                         @RequestParam(value = "offset",
                                                                 defaultValue = "0") Integer offset,
                                                         @RequestParam(value = "limit", defaultValue
                                                                 = "100") Integer limit) {
        List<ContentResponse> collect = contentQueryService.listContentBySearchKeyword(searchKeyword, offset, limit);
        return ResponseUtil.successResponse(HttpStatus.OK, collect);
    }
}
