package hello.springbootexternal;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommandLineBean {

  // 스프링이 시작될 때 알아서 등록해주는 빈 중에 하나
  // 필요한 클래스에서 의존성 주입 받아 커맨드 라인 옵션 인수값 사용 가능
  private final ApplicationArguments arguments;

  public CommandLineBean(ApplicationArguments arguments) {
    this.arguments = arguments;
  }

  @PostConstruct
  public void init() {
    log.info("source {}", List.of(arguments.getSourceArgs()));
    log.info("optionNames {}", arguments.getOptionNames());
    Set<String> optionNames = arguments.getOptionNames();
    for (String optionName : optionNames) {
      log.info("option args {}={}", optionName, arguments.getOptionValues(optionName));
    }
  }
}
