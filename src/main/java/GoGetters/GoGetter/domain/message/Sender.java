package GoGetters.GoGetter.domain.message;

import GoGetters.GoGetter.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Sender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sender_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Sender(Member member){
        this.member = member;

    }

}
