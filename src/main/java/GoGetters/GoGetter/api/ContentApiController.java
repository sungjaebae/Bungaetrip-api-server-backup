package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.content.Content;
import GoGetters.GoGetter.service.ContentService;
import GoGetters.GoGetter.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "contents")
public class ContentApiController {
    private ContentService contentService;

    @GetMapping(value = "")
    public ResponseEntity listTourContents(@RequestParam(value = "left")Double left,
                                           @RequestParam(value = "right")Double right,
                                           @RequestParam(value = "top")Double top,
                                           @RequestParam(value = "bottom")Double bottom,
                                           @RequestParam(value = "filter",required = false)String filter
                                           ) {
        List<Content> contentList = contentService.findAllByLocationAndFilter(left, right, top, bottom, filter);

        return ResponseUtil.successResponse(HttpStatus.OK,contentList);
    }


}
