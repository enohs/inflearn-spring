package hello.springbootexternal.external;

import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

// 인텔리제이 configuration 설정에서 -- 붙여주면 스프링으로 key=value 형식 변경 가능
@Slf4j
public class CommandLineV2 {

  public static void main(String[] args) {
    for (String arg : args) {
      log.info("arg {}", arg);
    }

    // 분리해주는 클래스
    ApplicationArguments appArgs = new DefaultApplicationArguments(args);
    log.info("SourceArgs={}", List.of(appArgs.getSourceArgs()));
    // -- 안 붙은 입력값만 따로 출력
    log.info("NonOptionArgs={}", appArgs.getNonOptionArgs());
    log.info("OptionNames={}", appArgs.getOptionNames());

    Set<String> optionNames = appArgs.getOptionNames();
    for (String optionName : optionNames) {
      log.info("option arg {}={}", optionName, appArgs.getOptionValues(optionName));
    }

    List<String> url = appArgs.getOptionValues("url");
    List<String> username = appArgs.getOptionValues("username");
    List<String> password = appArgs.getOptionValues("password");
    List<String> mode = appArgs.getOptionValues("mode");
    log.info("url={}", url);
    log.info("username={}", username);
    log.info("password={}", password);
    log.info("mode={}", mode); // null
  }

}
