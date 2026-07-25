package hello.springadv1.proxy.config.v2_dynamicproxy;

import hello.springadv1.proxy.app.v1.OrderControllerV1;
import hello.springadv1.proxy.app.v1.OrderControllerV1Impl;
import hello.springadv1.proxy.app.v1.OrderRepositoryV1;
import hello.springadv1.proxy.app.v1.OrderRepositoryV1Impl;
import hello.springadv1.proxy.app.v1.OrderServiceV1;
import hello.springadv1.proxy.app.v1.OrderServiceV1Impl;
import hello.springadv1.proxy.config.v2_dynamicproxy.handler.LogTraceBasicHandler;
import hello.springadv1.proxy.trace.logtrace.LogTrace;
import java.lang.reflect.Proxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicProxyBasicConfig {

  @Bean
  public OrderControllerV1 orderControllerV1Proxy(LogTrace logTrace) {
    OrderControllerV1 orderController = new OrderControllerV1Impl(orderServiceV1Proxy(logTrace));

    return (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(), new Class[]{OrderControllerV1.class}, new LogTraceBasicHandler(orderController, logTrace));
  }

  @Bean
  public OrderServiceV1 orderServiceV1Proxy(LogTrace logTrace) {
    OrderServiceV1 orderService = new OrderServiceV1Impl(orderRepositoryV1Proxy(logTrace));

    return (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(), new Class[]{OrderServiceV1.class}, new LogTraceBasicHandler(orderService, logTrace));
  }

  @Bean
  public OrderRepositoryV1 orderRepositoryV1Proxy(LogTrace logTrace) {
    OrderRepositoryV1 orderRepository = new OrderRepositoryV1Impl();

    return (OrderRepositoryV1) Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(), new Class[]{OrderRepositoryV1.class}, new LogTraceBasicHandler(orderRepository, logTrace));
  }

}
