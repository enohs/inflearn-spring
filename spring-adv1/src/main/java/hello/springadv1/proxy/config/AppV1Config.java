package hello.springadv1.proxy.config;

import hello.springadv1.proxy.app.v1.OrderControllerV1;
import hello.springadv1.proxy.app.v1.OrderControllerV1Impl;
import hello.springadv1.proxy.app.v1.OrderRepositoryV1;
import hello.springadv1.proxy.app.v1.OrderRepositoryV1Impl;
import hello.springadv1.proxy.app.v1.OrderServiceV1;
import hello.springadv1.proxy.app.v1.OrderServiceV1Impl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppV1Config {

  @Bean
  public OrderControllerV1 orderControllerProxyV1() {
    return new OrderControllerV1Impl(orderServiceProxyV1());
  }

  @Bean
  public OrderServiceV1 orderServiceProxyV1() {
    return new OrderServiceV1Impl(orderRepositoryProxyV1());
  }

  @Bean
  public OrderRepositoryV1 orderRepositoryProxyV1() {
    return new OrderRepositoryV1Impl();
  }
}
