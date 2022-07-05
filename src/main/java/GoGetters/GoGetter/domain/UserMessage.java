package GoGetters.GoGetter.domain;

import javax.persistence.*;

@Entity
public class UserMessage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_message_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    public UserMessage(User user,Message message){
        this.user=user;
        this.message=message;
    }
}
