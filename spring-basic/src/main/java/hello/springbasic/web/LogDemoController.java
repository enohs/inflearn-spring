package hello.springbasic.web;

import hello.springbasic.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

  private final LogDemoService logDemoService;
  // 의존관계 주입 받아야 함. 그런데 스코프라 request라 고객 요청이 있어야 생성이 됨 -> 컨테이너 안에 MyLogger가 없는데 달라고 해서 오류 발생!!
  /// -> Provider 사용 (Provider는 의존성 주입 시점에 의존성을 넣어줄 수 있음! 그리고 나중에 요청이 들어오면 그때 스프링 컨테이너에서 MyLogger 빈을 찾아서 반환해주는 것 WoW)
  // private final MyLogger myLogger;

  /// 더 나아가서 @Scope proxy 모드 쓰면 Provider 안 써도 됨
  //private final ObjectProvider<MyLogger> myLoggerProvider;
  private final MyLogger myLogger;

  @RequestMapping("log-demo")
  @ResponseBody // 뷰 없이 문자만 반환
  public String logDemo(HttpServletRequest request) {
    ///MyLogger myLogger = myLoggerProvider.getObject(); // 이 시점에 MyLogger가 빈 등록 됨

    // url을 myLogger에 담는데, 이때 request 스코프이기 때문에 각 요청마다 별도의 인스턴스가 생기므로 덮어쓰기 같은 걱정은 안 해도 됨
    String requestURL = request.getRequestURL().toString();

    System.out.println("myLogger = " + myLogger.getClass()); // MyLogger$$SpringCGLIB$$0 이때까지 프록시 클래스
    myLogger.setRequestURL(requestURL); // 객체의 실제 기능을 호출할 때 비로소 빈이 생성됨

    myLogger.log("controller test");
    logDemoService.logic("testId");
    return "OK";
  }


}
