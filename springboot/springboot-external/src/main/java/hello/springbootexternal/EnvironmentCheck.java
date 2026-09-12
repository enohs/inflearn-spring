package hello.springbootexternal;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EnvironmentCheck {

  // 모든 외부 설정 값을 Environment와 PropertySource로 추상화하여 설정이 바뀌어도 같은 코드를 그대로 사용이 가능하다
  // 자바 시스템 속성보다 커맨드 라인 옵션 인수가 우선순위를 가짐 - 더 유연한 것이 우선권을 가지고 범위가 넓은 것보다 더 좁은 것이 우선권을 가짐
  // 커맨드 라인 옵션 인수가 main의 args로만 들어오기 때문에 더 좁은 범위임
  private final Environment env;

  public EnvironmentCheck(Environment env) {
    this.env = env;
  }

  @PostConstruct
  public void init() {
    String url = env.getProperty("url");
    String username = env.getProperty("username");
    String password = env.getProperty("password");

    log.info("env url={}", url);
    log.info("env username={}", username);
    log.info("env password={}", password);
  }
}
