package GoGetters.GoGetter.domain;

import GoGetters.GoGetter.util.DateTimeUtils;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Sender sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Receiver receiver;

    private LocalDateTime createdAt;

    public Message(String content) {
        this.content=content;
        this.createdAt= DateTimeUtils.nowFromZone();
    }
    public Message(Sender sender,Receiver receiver,String content){
        this(content);
        this.sender=sender;
        this.receiver=receiver;
        this.receiver.getMessages().add(this);
    }

    public void setMembers(Sender sender,Receiver receiver){
        this.sender=sender;
        this.receiver=receiver;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
