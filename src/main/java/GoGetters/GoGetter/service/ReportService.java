package GoGetters.GoGetter.service;

import GoGetters.GoGetter.domain.ReportArticle;
import GoGetters.GoGetter.domain.ReportMember;
import GoGetters.GoGetter.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;

    public Long reportMember(ReportMember reportMember) {
        return reportRepository.saveMember(reportMember);
    }

    public Long reportArticle(ReportArticle reportArticle) {
        return reportRepository.saveArticle(reportArticle);
    }

}
