package hello.springbasic.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanListCycleTest1 {

  @Test
  public void lifeCycleTest() {
    // 보통 직접 닫는 일은 많이 없기 때문에 하위 인터페이스를 받는 것
    ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(lifeCycleConfig1.class);
    NetworkClient1 client = ac.getBean(NetworkClient1.class);
    ac.close();
  }

  @Configuration
  static class lifeCycleConfig1 {

    // 이 방법을 사용하면 NetworkClient1은 스프링 코드에 의존하지 않는다
    // 메서드 이름이 자유롭고, 설정 정보를 사용하기 때문에 코드 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드 적용 가능
    // destroyMethod 같은 경우에는 (inferred)라는 추론이라는 값이 기본값으로 지정되어 있다 -> 알아서 close와 shutdown이라는 이름의 메서드를 자동으로 추론해서 호출해준다 (따라서 생략 가능)
    // destroyMethod = ""로 작성하면 추론 기능 사용 안 함
    @Bean(initMethod = "init", destroyMethod = "close") // 초기화 콜백 메서드 이름과 소멸전 콜백 메서드 지정
    public NetworkClient1 networkClient() {
      NetworkClient1 networkClient = new NetworkClient1();
      networkClient.setUrl("http://hello-spring.dev");
      return networkClient;
    }
  }

}
