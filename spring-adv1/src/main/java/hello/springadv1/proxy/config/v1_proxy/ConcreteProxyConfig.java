package hello.springadv1.proxy.config.v1_proxy;

import hello.springadv1.proxy.app.v2.OrderControllerV2;
import hello.springadv1.proxy.app.v2.OrderRepositoryV2;
import hello.springadv1.proxy.app.v2.OrderServiceV2;
import hello.springadv1.proxy.config.v1_proxy.concrete_proxy.OrderControllerConcreteProxy;
import hello.springadv1.proxy.config.v1_proxy.concrete_proxy.OrderRepositoryConcreteProxy;
import hello.springadv1.proxy.config.v1_proxy.concrete_proxy.OrderServiceConcreteProxy;
import hello.springadv1.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcreteProxyConfig {

  @Bean
  public OrderControllerV2 orderControllerProxyV2(LogTrace logTrace) {
    OrderControllerV2 orderControllerImpl = new OrderControllerV2(orderServiceProxyV2(logTrace));
    return new OrderControllerConcreteProxy(orderControllerImpl, logTrace);
  }

  @Bean
  public OrderServiceV2 orderServiceProxyV2(LogTrace logTrace) {
    OrderServiceV2 orderServiceImpl = new OrderServiceV2(orderRepositoryProxyV2(logTrace));
    return new OrderServiceConcreteProxy(orderServiceImpl, logTrace);
  }

  @Bean
  public OrderRepositoryV2 orderRepositoryProxyV2(LogTrace logTrace) {
    OrderRepositoryV2 repositoryImpl = new OrderRepositoryV2();
    return new OrderRepositoryConcreteProxy(repositoryImpl, logTrace);
  }
}
