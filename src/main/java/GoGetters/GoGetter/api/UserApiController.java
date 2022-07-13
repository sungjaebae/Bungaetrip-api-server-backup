package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.Gender;
import GoGetters.GoGetter.domain.User;
import GoGetters.GoGetter.dto.RequestDto.CreateUserRequest;
import GoGetters.GoGetter.dto.ResponseDto.UserResponse;
import GoGetters.GoGetter.service.UserService;
import GoGetters.GoGetter.util.RequestUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserApiController {
    @Autowired
    FirebaseAuth firebaseAuth;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public Long register(
            @RequestHeader("Authorization") String authorization,
                         @RequestBody CreateUserRequest request) {
        System.out.println("controller init : ");
//        System.out.println(authorization);
        // TOKEN을 가져온다.
        FirebaseToken decodedToken;
        try {
//            String token = RequestUtil.getAuthorizationToken(authorization);
            String token="";
            System.out.println("controller token : "+ token);

            decodedToken = firebaseAuth.verifyIdToken(token);
            System.out.println("controller decodedToken : "+decodedToken);
        } catch (IllegalArgumentException | FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
        }

        System.out.println(decodedToken.getName());
        System.out.println(decodedToken.getEmail());
        System.out.println(decodedToken.getUid());
        System.out.println(decodedToken.getIssuer());
        System.out.println(decodedToken.getTenantId());
        System.out.println(request.getGender());
        System.out.println(request.getNickname());
        System.out.println(request.getAge());

        JSONObject jsonObject = new JSONObject(decodedToken.getClaims());
        System.out.println(jsonObject);
        User user = new User(decodedToken.getEmail(), decodedToken.getUid(), request.getNickname(),
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