package GoGetters.GoGetter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Member")
@Getter
@NoArgsConstructor
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String uid;
    private String email;

    private String nickName;
    private String password;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    public User(String email,String uid,String nickName, String password, Integer age, Gender gender) {
        this(email, uid);
        this.nickName=nickName;
        this.age=age;
        this.gender=gender;
        this.password=password;
    }
    public User(String email,String uid, String nickName, Integer age, Gender gender) {
        this(email, uid);
        this.nickName=nickName;
        this.age=age;
        this.gender=gender;
    }

    public User(String email, String uid) {
        this.email = email;
        this.uid = uid;
    }





    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
