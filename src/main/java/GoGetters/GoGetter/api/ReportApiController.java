package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.report.ReportedArticle;
import GoGetters.GoGetter.dto.report.ArticleReportRequest;
import GoGetters.GoGetter.dto.report.MemberReportRequest;
import GoGetters.GoGetter.service.MemberService;
import GoGetters.GoGetter.service.ReportService;
import GoGetters.GoGetter.util.JwtUtil;
import GoGetters.GoGetter.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/report")
@Slf4j
public class ReportApiController {
    private final ReportService reportService;
    private final JwtUtil jwtUtil;
    private final MemberService memberService;
    @Transactional
    @PostMapping(value = "/member")
    @Operation(summary = "회원 신고 접수 API",description = "회원번호와 신고 사유를 입력받아 신고를 접수받는다")
    public ResponseEntity createMemberReport(@RequestHeader("Authorization")String authorization,
                                             @RequestBody MemberReportRequest reportRequest) {
            String token = authorization.substring("Bearer ".length());
            log.debug("JWT token content : {}", token);
        Long reporterId = Long.parseLong(jwtUtil.getMemberId(token));
        log.info("JWT token memberId; {}",reporterId);

        Long reportedMemberId = reportService
                .reportMember(reportRequest.getMemberId(), reportRequest.getReportContent(), reporterId);


        return ResponseUtil.successResponse(HttpStatus.CREATED, new ReportResponse(reportedMemberId));
    }

    @Transactional
    @PostMapping(value = "/article")
    @Operation(summary = "게시글 신고 접수 API",description = "게시글 번호와 신고 사유를 입력받아 신고를 접수받는다")
    public ResponseEntity createArticleReport(@RequestBody ArticleReportRequest articleReportRequest ) {
        Long reportedArticleId=reportService.reportArticle(new ReportedArticle(articleReportRequest.getArticleId(), articleReportRequest.getReportContent()));
        return ResponseUtil.successResponse(HttpStatus.CREATED, new ReportResponse(reportedArticleId));
    }


    @Data
    @AllArgsConstructor
    class ReportResponse{
        private Long reportedId;
    }
}
