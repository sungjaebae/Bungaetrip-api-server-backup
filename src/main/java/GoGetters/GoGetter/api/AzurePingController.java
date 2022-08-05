package GoGetters.GoGetter.api;

import GoGetters.GoGetter.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j

public class AzurePingController {

    @GetMapping("/ping")
    public ResponseEntity ping(){
        return ResponseUtil.successResponse(HttpStatus.OK,"pong");
    }
}
