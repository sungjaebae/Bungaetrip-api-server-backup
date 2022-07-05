package GoGetters.GoGetter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Member")
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;



    public User(String email, String password, Integer age, Gender gender) {
        this.email=email;
        this.password=password;
        this.age=age;
        this.gender=gender;
    }
}
