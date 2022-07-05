package GoGetters.GoGetter.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class Receiver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receiver_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    public List<Message> messages=new ArrayList<>();

    public Receiver(User user) {
        this.user=user;
    }


}
