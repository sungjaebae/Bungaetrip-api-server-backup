package GoGetters.GoGetter.api;

import GoGetters.GoGetter.domain.User;
import GoGetters.GoGetter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApiController {
    private final UserService userService;
    //회원가입
//    @PostMapping("/signUp")
//    public Long join(@RequestBody CreateUserRequest createUserRequest) {
//
//
//    }
}
