package GoGetters.GoGetter.domain;


import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityMember  extends User {
    private static final Long serialVersionNickname=1L;

    public SecurityMember(Member member) {
        super(member.getUsername(),
                "{noop}"+ member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
    }
}
