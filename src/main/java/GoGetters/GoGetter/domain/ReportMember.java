package GoGetters.GoGetter.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ReportMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_member_id")
    private Long id;

    private Long memberId;

    private String content;

    public ReportMember(Long memberId, String content) {
        this.memberId=memberId;
        this.content=content;
    }

}
