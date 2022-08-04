package GoGetters.GoGetter.domain;

import GoGetters.GoGetter.dto.RequestDto.MemberInfoRequest;
import GoGetters.GoGetter.util.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

@Entity
@Table(name = "Member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member
        implements UserDetails
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String email;

    private String nickname;
    private String password;
 
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private UserRole role=UserRole.ROLE_USER;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    public Member(String username, String email, String password, String nickname, Integer age, Gender gender, String description) {
        this(username,email, password, nickname, age, gender);
        this.description=description;
    }
    public Member(String username, String email, String password, String nickname, Integer age, Gender gender) {
        this(email, password, nickname, age, gender);
        this.username=username;
    }

    public Member(String email, String password, String nickname, Integer age, Gender gender, String uid) {

        this(email, uid);
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.password = password;
    }
    public Member(String email, String password, String nickname, Integer age, Gender gender) {
        this.email=email;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.password = password;
    }


    public Member(String email, String username) {
        this.email = email;
        this.username = username;
        this.createdAt= DateTimeUtils.nowFromZone();
        this.deletedAt=null;
    }

    public Member(String username) {
        this.username=username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                '}';
    }

    public void updateMyInfo(MemberInfoRequest memberInfoRequest) {
        this.nickname= memberInfoRequest.getNickname();
        this.age= memberInfoRequest.getAge();
        this.gender=Gender.valueOf(memberInfoRequest.getGender()) ;
        this.description= memberInfoRequest.getDescription();
    }
    public void encodePassword(String enCodedPassword) {
        this.password=enCodedPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
///
}
