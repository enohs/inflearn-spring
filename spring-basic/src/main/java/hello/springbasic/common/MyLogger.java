package hello.springbasic.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.UUID;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

// http 요청당 하나씩 생성되고 요청이 끝나는 시점에 소멸
@Component
//@Scope("request")
// 프록시 모드 쓰면 Provider 안 써도 됨 oOo
/// 프로바이더처럼 가짜 프록시 클래스를 만들어두고 request 상관 없이 다른 빈에 미리 주입해 둘 수 있다 (class에서는 _CLASS, 인터페이스에서는 INTERFACE로 쓰면 됨)
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

  private String uuid;
  private String requestURL;

  public void setRequestURL(String requestURL) {
    this.requestURL = requestURL;
  }

  public void log(String message) {
    System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
  }

  @PostConstruct
  public void init() {
    uuid = UUID.randomUUID().toString();
    System.out.println("[" + uuid + "] request scope bean created: " + this);
  }

  // request 스코프는 프로토타입이랑 다르게 종료까지 스프링 컨테이너에서 관리해줌
  @PreDestroy
  public void close() {
    System.out.println("[" + uuid + "] request scope bean closed: " + this);
  }

}
