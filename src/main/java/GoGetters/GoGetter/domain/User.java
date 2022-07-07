package GoGetters.GoGetter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Member")
@Getter
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String nickName;
    private String password;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;



    public User(String email,String nickName, String password, Integer age, Gender gender) {
        this.email=email;
        this.nickName=nickName;
        this.password=password;
        this.age=age;
        this.gender=gender;
    }
}
