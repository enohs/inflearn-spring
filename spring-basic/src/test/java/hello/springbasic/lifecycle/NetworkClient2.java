package hello.springbasic.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class NetworkClient2 {

  private String url;

  public NetworkClient2() {
    System.out.println("생성자 호출, url = " + url);
  }

  public void setUrl(String url) {
    this.url = url;
  }

  // 서비스 시작 시 호출
  public void connect() {
    System.out.println("connect: " + url);
  }

  public void call(String message) {
    System.out.println("call: " + url + " message = " + message);
  }

  public void disconnect() {
    System.out.println("close: " + url);
  }

  // 서비스 종료 시 호출
  // 아래 두 어노테이션이 있으면 초기화 콜백, 소멸 전 콜백을 받아 자동 실행해줌
  // 외부 라이브러리에 적용할 때는 @Bean(initMethod, destroyMethod)를 사용하자
  // 이 방법을 사용하면 됨
  @PostConstruct
  public void init() {
    System.out.println("NetworkClient2.init");
    connect();
    call("초기화 연결 메시지");
  }

  @PreDestroy
  public void close() {
    System.out.println("NetworkClient2.close");
    disconnect();
  }
}
