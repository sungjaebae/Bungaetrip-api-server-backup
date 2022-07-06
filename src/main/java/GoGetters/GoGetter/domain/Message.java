package GoGetters.GoGetter.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @NoArgsConstructor
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;


    private String content;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Sender sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Receiver receiver;

    private LocalDateTime created;

    public Message(Sender sender,Receiver receiver,String content){
        this.sender=sender;
        this.receiver=receiver;
        this.receiver.getMessages().add(this);
        this.content=content;
    }
}
