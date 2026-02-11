package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller를 사용하면 메서드의 반환값을 뷰의 이름으로 판단함
@RestController// 문자를 반환하면 HTTP 메시지 바디에 바로 입력함
@Slf4j
public class LogTestController {

  // @slf4j가 아래 코드를 대신 작성해줌
  //private final Logger log = LoggerFactory.getLogger(getClass());

  @GetMapping("/log-test")
  public String logTest() {
    String name = "Spring";

    // println은 log처럼 레벨에 따라 보기 원하는 로그를 설정하기 어렵다는 문제가 있음
    // 로그만큼 많은 의미를 담고 있지도 않음
    // -> 실무에서는 안 씀
    System.out.println("name = " + name);

    // 이렇게 쓰면 안 됨
    // 지연연산이 아닌 즉시연산이 일어나서 로그 레벨에 따라 무의미한 연산이 실행되기 때문!
    // log.trace("trace log=" + name);

    log.trace("trace log={}", name);
    log.debug("debug log={}", name);
    log.info("info log={}", name);
    log.warn("warn log={}", name);
    log.error("error log={}", name);

    return "ok";
  }


}
