package GoGetters.GoGetter.service.query;

import GoGetters.GoGetter.MessageResource;
import GoGetters.GoGetter.domain.member.Member;
import GoGetters.GoGetter.dto.member.MemberInfoReturn;
import GoGetters.GoGetter.exception.Member.NoSuchMemberException;
import GoGetters.GoGetter.repository.MemberRepository;
import GoGetters.GoGetter.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public MemberInfoReturn readMember(String authorization) {
        String token = authorization.substring("Bearer ".length());
        String username = jwtUtil.getUsername(token);
        List<Member> memberByUsername = memberRepository.findMembersByUsername(username);
        if (memberByUsername.size() == 0) {
            throw new NoSuchMemberException(MessageResource.memberNotExist);
        }
        Member member = memberByUsername.get(0);
        return new MemberInfoReturn(member);
    }

    public MemberInfoReturn readMemberById(Long memberId) {
        Member member = memberRepository.findOne(memberId);
        return new MemberInfoReturn(member);
    }


}
