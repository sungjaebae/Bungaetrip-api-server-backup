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

    Message(String content){
        this.content=content;
    }
}
