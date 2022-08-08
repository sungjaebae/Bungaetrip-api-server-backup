package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.ReportArticle;
import GoGetters.GoGetter.domain.ReportMember;
import GoGetters.GoGetter.dto.report.ArticleReportRequest;
import GoGetters.GoGetter.dto.report.MemberReportRequest;
import GoGetters.GoGetter.service.ReportService;
import GoGetters.GoGetter.util.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    public ResponseEntity createMemberReport(@RequestBody MemberReportRequest reportRequest) {
        Long reportedMemberId=reportService.reportMember(new ReportMember(reportRequest.getMemberId(),reportRequest.getReportContent()));

        return ResponseUtil.successResponse(HttpStatus.CREATED, new ReportResponse(reportedMemberId));
    }

    @Transactional
    @PostMapping(value = "/article")
    public ResponseEntity createArticleReport(@RequestBody ArticleReportRequest articleReportRequest ) {
        Long reportedArticleId=reportService.reportArticle(new ReportArticle(articleReportRequest.getArticleId(), articleReportRequest.getReportContent()));
        return ResponseUtil.successResponse(HttpStatus.CREATED, new ReportResponse(reportedArticleId));
    }





    @Data
    @AllArgsConstructor
    class ReportResponse{
        private Long reportedId;
    }
}
