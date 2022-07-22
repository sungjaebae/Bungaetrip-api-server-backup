package GoGetters.GoGetter.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {
    @GetMapping("/test")
    public void test(){
        String name = "test";
        log.trace("trace {} ", name);
        log.debug("debug {} {}", name,name);
        log.info("info {}", name);
        log.warn("warn {}", name);
        log.error("error {}",name);
    }
}
