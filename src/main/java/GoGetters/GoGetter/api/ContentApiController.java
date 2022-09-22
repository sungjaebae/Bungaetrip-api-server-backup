package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.article.Article;
import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.domain.content.ContentType;
import GoGetters.GoGetter.dto.content.ContentWithArticlesResponse;
import GoGetters.GoGetter.dto.content.ListContentResponse;
import GoGetters.GoGetter.service.ArticleService;
import GoGetters.GoGetter.service.ContentService;
import GoGetters.GoGetter.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "contents")
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

        List<Content> contentList = contentService.findPlaceInAreaByFilter(left, right, top, bottom,
                filter);
        List<ListContentResponse> collect = contentList.stream().map(content -> new ListContentResponse(content))
                .collect(Collectors.toList());
        return ResponseUtil.successResponse(HttpStatus.OK, collect);
    }

    @GetMapping(value = "/{contentId}")
    public ResponseEntity readTourContent(@PathVariable(value = "contentId") Long contentId) {
        Content content = contentService.findOne(contentId);
        List<Article> articles = articleService.findArticlesByLocation(content.getTitle());

        return ResponseUtil.successResponse(HttpStatus.OK, new ContentWithArticlesResponse(content, articles));
    }

    @GetMapping(value = "/recommend")
    public ResponseEntity listRecommendContent() {
//        contentService.findContentsWhereOtherLeanTo()
        return ResponseUtil.successResponse(HttpStatus.OK, "");
    }

    @GetMapping(value = "",params = "searchKeyword")
    public ResponseEntity listTourContentBySearchKeyword(@RequestParam(value = "searchKeyword") String searchKeyword) {
        List<Content> contentList=contentService.findAllBySearchKeyword(searchKeyword);
        List<ListContentResponse> collect = contentList.stream().map(content -> new ListContentResponse(content)).collect(Collectors.toList());
        return ResponseUtil.successResponse(HttpStatus.OK, collect);
    }
}
