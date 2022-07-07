package GoGetters.GoGetter.domain;

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
    @JoinColumn(name = "user_id")
    private User user;

    public Sender(User user){
        this.user=user;
    }
}
