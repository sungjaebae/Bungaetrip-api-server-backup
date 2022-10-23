package GoGetters.GoGetter.service.query;

import GoGetters.GoGetter.domain.member.Member;
import GoGetters.GoGetter.dto.fcmToken.FcmTokenRequest;
import GoGetters.GoGetter.repository.MemberRepository;
import GoGetters.GoGetter.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FCMQueryService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public Member setFcmToken(String authorization, FcmTokenRequest fcmTokenRequest) {
        log.debug("JWT authorization : {}", authorization);
        String token = authorization.substring("Bearer ".length());
        log.debug("JWT token content : {}", token);
        Long memberId = Long.parseLong( jwtUtil.getMemberId(token));
        Member member = memberRepository.findOne(memberId);
        log.debug("handle createFcmToken {}", fcmTokenRequest.getFcmToken());
        member.enrollFcmToken(fcmTokenRequest.getFcmToken());
        return member;
    }


}
