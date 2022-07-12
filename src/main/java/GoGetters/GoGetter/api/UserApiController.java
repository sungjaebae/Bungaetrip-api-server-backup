package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.Gender;
import GoGetters.GoGetter.domain.User;
import GoGetters.GoGetter.dto.RequestDto.CreateUserRequest;
import GoGetters.GoGetter.dto.ResponseDto.UserResponse;
import GoGetters.GoGetter.service.UserService;
import GoGetters.GoGetter.util.RequestUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApiController {
    private final FirebaseAuth firebaseAuth;

    private final UserService userService;

    @PostMapping("")
    public Long register(@RequestHeader("Authorization") String authorization,
                         @RequestBody CreateUserRequest request) {
        System.out.println("token init");
        System.out.println(authorization);
        // TOKEN을 가져온다.
        FirebaseToken decodedToken;
        try {
            String token = RequestUtil.getAuthorizationToken(authorization);
            System.out.println(token);
            System.out.println("verify token");
            decodedToken = firebaseAuth.verifyIdToken(token);
            System.out.println("email");
            System.out.println(decodedToken.getEmail());
            System.out.println("name");
            System.out.println(decodedToken.getName());
        } catch (IllegalArgumentException | FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
        }


        // 사용자를 등록한다.
        User user = new User(decodedToken.getEmail(), decodedToken.getUid(), request.getNickName(),
                request.getAge(), request.getGender());
        Long saved = userService.join(user);
        return saved;
    }

    @GetMapping("/me")
    public UserResponse getUserMe(Authentication authentication) {
        User customUser = ((User) authentication.getPrincipal());
        return new UserResponse(customUser);
    }

    @GetMapping("/redirect")
    public void redirect(){
        System.out.println("test gmail");
    }
}