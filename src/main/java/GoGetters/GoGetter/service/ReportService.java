package GoGetters.GoGetter.service;

import GoGetters.GoGetter.MessageResource;
import GoGetters.GoGetter.domain.message.Member;
import GoGetters.GoGetter.domain.report.ReportedArticle;
import GoGetters.GoGetter.domain.report.ReportedMember;
import GoGetters.GoGetter.exception.Member.NoSuchMemberException;
import GoGetters.GoGetter.exception.report.AlreadyReportException;
import GoGetters.GoGetter.repository.MemberRepository;
import GoGetters.GoGetter.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;

    public Long reportMember(Long reportedMemberId,String reportContent,Long reporterId) {
        validateDuplicatedReportedMember(reportedMemberId,reporterId);
        List<Member> member = memberRepository.findOne(reporterId);
        log.info("reporterId {}",reporterId);
        log.info("reportedMemberId {}",reportedMemberId);
        if (member.isEmpty()) {
            throw new NoSuchMemberException(MessageResource.memberNotExist);
        }

        return reportRepository.saveMember(new ReportedMember(reportedMemberId,reportContent,member.get(0)));
    }
    private void validateDuplicatedReportedMember(Long reportedMemberId,Long reporterId) {
        List<ReportedMember> reportMember = reportRepository.findReportMember(reportedMemberId, reporterId);
        log.debug("reportedMember count: {}", reportMember.size());
        log.debug("reportedMemberId :{}",reportedMemberId);
        log.debug("reporterId :{}",reporterId);
        if (!reportMember.isEmpty()) {
            throw new AlreadyReportException(MessageResource.alreadyReportedMemberExist);
        }
    }


    public Long reportArticle(ReportedArticle reportedArticle) {
        return reportRepository.saveArticle(reportedArticle);
    }

}
