package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {
    // private final Logger log = LoggerFactory.getLogger(getClass()); -> @Slf4j

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        // log.trace(" trace log=" + name); -> 이렇게 사용하면 안되는 이유
        // + 더하는 연산이 일어난다 -> 메모리, CPU 사용 -> 비효율적이다
        // {} 이렇게 사용하면 연산도 일어나지 않는다 -> 효율적이다
        log.trace(" trace log={}", name);
        log.debug("debug log={}", name); // debug 개발 서버에서 보는 로그
        log.info(" info log={}", name); // 비즈니스 정보
        log.warn(" warn log={}", name); // 위험한 것
        log.error("error log={}", name); // 에러상황

        return "ok";
    }

}
