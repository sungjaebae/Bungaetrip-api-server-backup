package GoGetters.GoGetter.domain.report;

import GoGetters.GoGetter.domain.message.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ReportedMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_member_id")
    private Long id;

    private Long reportedMemberId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member reporter;

    public ReportedMember(Long reportedMemberId, String content, Member reporter) {
        this.reportedMemberId = reportedMemberId;
        this.content = content;
        this.reporter = reporter;
        this.reporter.addBlockedMember(this);
    }

}
