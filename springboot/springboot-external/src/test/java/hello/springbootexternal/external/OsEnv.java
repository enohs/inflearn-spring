package hello.springbootexternal.external;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

// OS 환경변수 불러오기
@Slf4j
public class OsEnv {

  public static void main(String[] args) {
    Map<String, String> envMap = System.getenv();
    for (String key : envMap.keySet()) {
      log.info("env {}={}", key, System.getenv(key));
    }
  }

}
