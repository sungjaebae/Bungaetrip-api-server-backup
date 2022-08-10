package GoGetters.GoGetter.api;


import GoGetters.GoGetter.util.FirebaseSender;
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
//    private final FirebaseSender firebaseSender;

    @PostMapping(value = "/fcmToken")
    public ResponseEntity createFcmToken(@RequestHeader("Authorization") String authorization
    , @RequestBody String fcmToken) {
        log.debug("handle createFcmToken {}", fcmToken);

        return ResponseUtil.successResponse(HttpStatus.OK, fcmToken);
    }
}
