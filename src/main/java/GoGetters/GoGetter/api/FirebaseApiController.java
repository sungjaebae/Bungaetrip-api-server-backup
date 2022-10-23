package GoGetters.GoGetter.api;


import GoGetters.GoGetter.domain.member.Member;
import GoGetters.GoGetter.dto.fcmToken.FcmTokenRequest;
import GoGetters.GoGetter.service.MemberService;
import GoGetters.GoGetter.service.query.FCMQueryService;
import GoGetters.GoGetter.util.JwtUtil;
import GoGetters.GoGetter.util.ResponseUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
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
    private final MemberService memberService;
    private final FCMQueryService fcmQueryService;
    private final JwtUtil jwtUtil;

    @PostMapping(value = "/fcmToken")
    @Operation(summary= "Fcm Token 저장", description = "쪽지 알림에 필요한 사용자 디바이스 정보를 해당 API에서 입력받아 데이터베이스에 저장한다")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "jwt token",dataType = "String",required = true,paramType = "header"),
            @ApiImplicitParam(name="fcmToken",value = "디바이스 토큰",dataType = "String",required = true,paramType = "query")
    })
    public ResponseEntity createFcmToken(@RequestHeader("Authorization") String authorization
            , @RequestBody FcmTokenRequest fcmTokenRequest) {

        Member member = fcmQueryService.setFcmToken(authorization, fcmTokenRequest);
        return ResponseUtil.successResponse(HttpStatus.OK, member.getFcmToken());
    }


    @PatchMapping(value = "/fcmToken")
    @Operation(summary = "Fcm Token 수정", description = "사용자의 디바이스 정보를 입력받아 수정한다")
    public ResponseEntity updateFcmToken(@RequestHeader("Authorization") String authorization
            , @RequestBody FcmTokenRequest fcmTokenRequest) {
        Member member = fcmQueryService.setFcmToken(authorization, fcmTokenRequest);

        return ResponseUtil.successResponse(HttpStatus.OK, member.getFcmToken());
    }
}
