package hello.springadv1.proxy.config;

import hello.springadv1.proxy.app.v2.OrderControllerV2;
import hello.springadv1.proxy.app.v2.OrderRepositoryV2;
import hello.springadv1.proxy.app.v2.OrderServiceV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppV2Config {

  @Bean
  public OrderControllerV2 orderControllerProxyV2() {
    return new OrderControllerV2(orderServiceProxyV2());
  }

  @Bean
  public OrderServiceV2 orderServiceProxyV2() {
    return new OrderServiceV2(orderRepositoryProxyV2());
  }

  @Bean
  public OrderRepositoryV2 orderRepositoryProxyV2() {
    return new OrderRepositoryV2();
  }
}
