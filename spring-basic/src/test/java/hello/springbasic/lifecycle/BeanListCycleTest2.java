package hello.springbasic.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanListCycleTest2 {

  @Test
  public void lifeCycleTest() {
    // 보통 직접 닫는 일은 많이 없기 때문에 하위 인터페이스를 받는 것
    ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(lifeCycleConfig.class);
    NetworkClient2 client = ac.getBean(NetworkClient2.class);
    ac.close();
  }

  @Configuration
  static class lifeCycleConfig {

    @Bean
    public NetworkClient2 networkClient() {
      NetworkClient2 networkClient = new NetworkClient2();
      networkClient.setUrl("http://hello-spring.dev");
      return networkClient;
    }
  }

}
