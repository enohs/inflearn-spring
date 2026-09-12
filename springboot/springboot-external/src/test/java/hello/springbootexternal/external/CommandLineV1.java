package hello.springbootexternal.external;

import lombok.extern.slf4j.Slf4j;

// 메인 메서드 파라미터에 값 넣기
// 이 경우에는 key=value 형식이 아닌 통문자로만 인식함
@Slf4j
public class CommandLineV1 {

  public static void main(String[] args) {
    for (String arg : args) {
      log.info("arg {}", arg);
    }
  }

}
