package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.ReportArticle;
import GoGetters.GoGetter.domain.ReportMember;
import GoGetters.GoGetter.service.ReportService;
import GoGetters.GoGetter.util.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/report")
public class ReportApiController {
    private final ReportService reportService;

    @Transactional
    @PostMapping(value = "/member")
    public ResponseEntity createMemberReport(@RequestParam("memberId") Long memberId
            , @RequestParam("reportContent") String reportContent) {
        Long reportedMemberId=reportService.reportMember(new ReportMember(memberId,reportContent));

        return ResponseUtil.successResponse(HttpStatus.CREATED, new ReportResponse(reportedMemberId));
    }

    @Transactional
    @PostMapping(value = "/article")
    public ResponseEntity createArticleReport(@RequestParam("articleId") Long articleId,
                                              @RequestParam("reportContent") String reportContent) {
        Long reportedArticleId=reportService.reportArticle(new ReportArticle(articleId,reportContent));
        return ResponseUtil.successResponse(HttpStatus.CREATED, new ReportResponse(reportedArticleId));
    }
    @Data
    @AllArgsConstructor
    class ReportResponse{
        private Long reportedId;
    }
}
