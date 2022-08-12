package GoGetters.GoGetter.api;

import GoGetters.GoGetter.MessageResource;
import GoGetters.GoGetter.domain.Gender;
import GoGetters.GoGetter.domain.Member;
import GoGetters.GoGetter.dto.RequestDto.LoginRequest;
import GoGetters.GoGetter.dto.RequestDto.MemberInfoRequest;
import GoGetters.GoGetter.dto.ResponseDto.UserResponse;
import GoGetters.GoGetter.exception.Member.InvalidUpdateMemberInfoException;
import GoGetters.GoGetter.exception.Member.NoSuchMemberException;
import GoGetters.GoGetter.service.MemberService;
import GoGetters.GoGetter.util.BlobStorage;
import GoGetters.GoGetter.util.CookieUtil;
import GoGetters.GoGetter.util.JwtUtil;
//import GoGetters.GoGetter.util.RedisUtil;
//import com.google.firebase.auth.FirebaseAuth;
import GoGetters.GoGetter.util.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {
//    final FirebaseAuth firebaseAuth;

    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    private final BlobStorage blobStorage;

    @GetMapping(value = "/{memberId}")
    public ResponseEntity readMemberById(@PathVariable("memberId") Long memberId) {
        Member member = memberService.findOne(memberId);
        MemberInfoReturn memberResponse=new MemberInfoReturn(member);
        return ResponseUtil.successResponse(HttpStatus.OK,memberResponse);
    }
    @PatchMapping(value = "/myInfo", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity updateMemberInfo(@RequestPart MemberInfoRequest request,
                                           @RequestPart(required = false) MultipartFile imgFile) throws IOException {
        if (!request.getGender().equals("MALE") && !request.getGender().equals("FEMALE")) {
            throw new InvalidUpdateMemberInfoException(MessageResource.invalidMemberRequestForm);
        }
        String imageBlobUrl = null;
        if (imgFile != null) {
            imageBlobUrl = blobStorage.uploadFile(imgFile);
        }
        log.info("blob url {}",imageBlobUrl);
        Long updatedId=memberService.updateMyInfo(request,imageBlobUrl);

        return ResponseUtil.successResponse(HttpStatus.OK, updatedId);
    }

    @GetMapping(value = "/username")
    public ResponseEntity validateUsername(@RequestParam("username") String username) {
        log.debug("Log /member/username");
        List<Member> membersByUsername= memberService.findMemberByUsername(username);
        log.debug("Log /member/username size : {}",membersByUsername.size());
        Boolean isAvailable=true;
        if (membersByUsername.size() != 0)
            isAvailable=false;
        return ResponseUtil.successResponse(HttpStatus.OK,new IsValidate(username,isAvailable));
    }

    @GetMapping(value = "/email")
    public ResponseEntity validateEmail(@RequestParam("email") String email) {
        log.debug("Log /member/email");
        List<Member> membersByEmail= memberService.findMemberByEmail(email);
        log.debug("Log /member/email size : {}",membersByEmail.size());
        Boolean isAvailable=true;
        if (membersByEmail.size() != 0)
            isAvailable=false;
        return ResponseUtil.successResponse(HttpStatus.OK,new IsValidate(email,isAvailable));
    }

    @GetMapping(value = "")
    public ResponseEntity readMember(
            @RequestHeader("Authorization") String authorization) {
        log.debug("JWT authorization : {}",authorization);
        String token = authorization.substring("Bearer ".length());
        log.debug("JWT token content : {}", token);
        String username = jwtUtil.getUsername(token);
        log.debug("JWT token claim username : {}",username);
        List<Member> memberByUsername = memberService.findMemberByUsername(username);
        if (memberByUsername.size() == 0) {
            throw new NoSuchMemberException(MessageResource.memberNotExist);
        }


        Member member = memberByUsername.get(0);
        return ResponseUtil.successResponse(HttpStatus.OK, new MemberInfoReturn(member));

    }
    @Data
    @AllArgsConstructor
    class MemberInfoReturn{
        private Long memberId;
        private String username;
        private String email;
        private String nickname;
        private Integer age;

        private String gender;
        private String description;
        private String profileUrl;
        private List<Long> blockedPeople;
        public MemberInfoReturn(Member member) {
            this.memberId=member.getId();
            this.username=member.getUsername();
            this.email=member.getEmail();
            this.nickname=member.getNickname();
            this.age= member.getAge();


            this.gender=null;
            if(member.getGender()!=null)
                this.gender=member.getGender().toString();
            this.description=member.getDescription();

            this.blockedPeople= member.getBlockedPeople().stream()
                    .map(blockedPeople -> blockedPeople.getReportedMemberId())
                    .collect(Collectors.toList());
            this.profileUrl=member.getProfileUrl();
        }
    }
    @Data
    @AllArgsConstructor
    class IsValidate {
        private String userInput;
        private Boolean IsAvailable;
    }
//    @PostMapping("")
//    public Long register(
//            @RequestHeader("Authorization") String authorization,
//            @RequestBody CreateUserFirebaseRequest request
//    ) {
//        // TOKEN을 가져온다.
//        FirebaseToken decodedToken;
//        try {
//            String token = RequestUtil.getAuthorizationToken(authorization);
////            String token="";
//
//            decodedToken = firebaseAuth.verifyIdToken(token);
//        } catch (IllegalArgumentException | FirebaseAuthException e) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
//                    "{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
//        }
//
//        JSONObject jsonObject = new JSONObject(decodedToken.getClaims());
//        Member member = new Member(decodedToken.getEmail(), decodedToken.getUid());
//        Long saved = null;
//        try {
//            saved = memberService.join(member);
//        } catch (InterruptedException e) {
//            //여기서 기존가입한 회원의 정보를 수정하면 됨.
//            throw new RuntimeException("이미 회원이 존재합니다", e);
//        }
//        return saved;
//    }

//    @GetMapping("/me")
//    public UserResponse getUserMe(Authentication authentication) {
//        Member customMember = ((Member) authentication.getPrincipal());
//        return new UserResponse(customMember);
//    }

//    @PostMapping("/signup")
//    public Result signUpUser(@RequestBody CreateUserRequest request) {
//        Member member = new Member(request.getUsername(), request.getEmail(), request.getPassword(),
//                request.getNickname(), request.getAge(), request.getGender());
//        try {
//            memberService.join(member);
//        } catch (Exception e) {
//            throw new IllegalStateException("회원가입을 하는 중 오류가 발생했습니다.", e);
//        }
//
//        return new Result(null);
//    }

//    @PostMapping("/login")
//    public Response login(
////            @RequestHeader("Authorization") String authorization,
//            @RequestBody LoginRequest loginRequest,
//                        HttpServletRequest request,
//                        HttpServletResponse response) {
//        try {
//            log.debug("login api 들어왔습니다");
//            final Member member = memberService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
//            log.debug("member object {}",member);
//            final String token = jwtUtil.generateToken(member);
//            log.debug("토큰이 생성되었습니다. : {}",token);
////            final String refreshJwt = jwtUtil.generateRefreshToken(member);
//            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
////            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
////            redisUtil.setDataExpire(refreshJwt, member.getUsername(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
//            response.addCookie(accessToken);
////            response.addCookie(refreshToken);
//
//            return new Response("success", "로그인에 성공했습니다", token);
//        } catch (Exception e) {
//            return (new Response("error", "로그인에 실패했습니다.", e.getMessage()));
//        }
//    }
//

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Response {
        private String code;
        private String message;
        private String token;

    }
}