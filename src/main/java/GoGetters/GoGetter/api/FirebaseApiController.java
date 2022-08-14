package GoGetters.GoGetter.api;


import GoGetters.GoGetter.domain.member.Member;
import GoGetters.GoGetter.dto.fcmToken.FcmTokenRequest;
import GoGetters.GoGetter.service.MemberService;
import GoGetters.GoGetter.util.FirebaseSender;
import GoGetters.GoGetter.util.JwtUtil;
import GoGetters.GoGetter.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class FirebaseApiController {
    private final FirebaseSender firebaseSender;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @PostMapping(value = "/fcmToken")
    public ResponseEntity createFcmToken(@RequestHeader("Authorization") String authorization
            , @RequestBody FcmTokenRequest fcmTokenRequest) {
        log.debug("JWT authorization : {}", authorization);
        String token = authorization.substring("Bearer ".length());
        log.debug("JWT token content : {}", token);
        Long memberId = Long.parseLong(jwtUtil.getMemberId(token));
        Member one = memberService.findOne(memberId);
        log.debug("handle createFcmToken {}", fcmTokenRequest.getFcmToken());
        memberService.enrollFcmToken(one, fcmTokenRequest.getFcmToken());
        return ResponseUtil.successResponse(HttpStatus.OK, one.getFcmToken());
    }

    @PatchMapping(value = "/fcmToken")
    public ResponseEntity updateFcmToken(@RequestHeader("Authorization") String authorization
            , @RequestBody FcmTokenRequest fcmTokenRequest) {
        log.debug("JWT authorization : {}", authorization);
        String token = authorization.substring("Bearer ".length());
        log.debug("JWT token content : {}", token);
        Long memberId = Long.parseLong(jwtUtil.getMemberId(token));
        Member one = memberService.findOne(memberId);
        log.debug("handle createFcmToken {}", fcmTokenRequest.getFcmToken());
        memberService.updateFcmToken(one, fcmTokenRequest.getFcmToken());
        return ResponseUtil.successResponse(HttpStatus.OK, one.getFcmToken());
    }
}
