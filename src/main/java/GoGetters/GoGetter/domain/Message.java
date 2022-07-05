package GoGetters.GoGetter.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
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
    public Message(Sender sender,Receiver receiver,String content){
        this.sender=sender;
        this.receiver=receiver;
        this.receiver.getMessages().add(this);
        this.content=content;
    }
}
