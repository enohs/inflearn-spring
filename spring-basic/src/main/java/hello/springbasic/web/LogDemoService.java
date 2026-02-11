package hello.springbasic.web;

import hello.springbasic.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

  /// 컨트롤러와 마찬가지로 의존성 주입 시점에는 request 스코프인 MyLogger가 빈 등록이 안 되어 있기 때문에 Provider를 대신 주입 받고 필요할 때 꺼내 씀
  //private final MyLogger myLogger;
  //private final ObjectProvider<MyLogger> myLoggerProvider;

  // scope 프록시 모드 사용
  private final MyLogger myLogger;

  public void logic(String id) {
    //MyLogger myLogger = myLoggerProvider.getObject();
    myLogger.log("service id = " + id);
  }
}
