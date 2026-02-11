package hello.springbasic.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

// 의존관계 설정 후 초기화 콜백 받는 방법 1. 인터페이스 활용 (초창기 방법. 이제는 잘 안 사용하는 방법)
// 다만 이 인터페이스는 스프링 의존적이고 메서드 이름 변경 불가.
// 내가 고칠 수 없는 외부 라이브러리에는 적용할 수 없음
public class NetworkClient implements InitializingBean, DisposableBean {

  private String url;

  public NetworkClient() {
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

  // 서비스 종료 시 호출
  public void disconnect() {
    System.out.println("close: " + url);
  }

  // 의존관계 연결이 끝나면 자동으로 호출되는 메서드
  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("NetworkClient.afterPropertiesSet");
    connect();
    call("초기화 메시지");
  }

  // 빈 생명주기 후 스프링 컨테이너 종료 직전에 자동으로 동작하는 메서드
  @Override
  public void destroy() throws Exception {
    System.out.println("NetworkClient.destroy");
    disconnect();
  }
}
