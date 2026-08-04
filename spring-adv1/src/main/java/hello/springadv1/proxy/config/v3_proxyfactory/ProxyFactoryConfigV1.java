package hello.springadv1.proxy.config.v3_proxyfactory;

import hello.springadv1.proxy.app.v1.OrderControllerV1;
import hello.springadv1.proxy.app.v1.OrderControllerV1Impl;
import hello.springadv1.proxy.app.v1.OrderRepositoryV1;
import hello.springadv1.proxy.app.v1.OrderRepositoryV1Impl;
import hello.springadv1.proxy.app.v1.OrderServiceV1;
import hello.springadv1.proxy.app.v1.OrderServiceV1Impl;
import hello.springadv1.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.springadv1.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProxyFactoryConfigV1 {

  @Bean
  public OrderControllerV1 orderControllerV1Proxy(LogTrace logTrace) {
    OrderControllerV1Impl orderController = new OrderControllerV1Impl(orderServiceV1Proxy(logTrace));

    ProxyFactory factory = new ProxyFactory(orderController);
    factory.addAdvisor(getAdvisor(logTrace));
    OrderControllerV1 proxy = (OrderControllerV1) factory.getProxy();

    log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderController.getClass());

    return proxy;
  }

  @Bean
  public OrderServiceV1 orderServiceV1Proxy(LogTrace logTrace) {
    OrderServiceV1Impl orderService = new OrderServiceV1Impl(orderRepositoryV1Proxy(logTrace));

    ProxyFactory factory = new ProxyFactory(orderService);
    factory.addAdvisor(getAdvisor(logTrace));
    OrderServiceV1 proxy = (OrderServiceV1) factory.getProxy();

    log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderService.getClass());

    return proxy;
  }

  @Bean
  public OrderRepositoryV1 orderRepositoryV1Proxy(LogTrace logTrace) {
    OrderRepositoryV1Impl orderRepository = new OrderRepositoryV1Impl();

    ProxyFactory factory = new ProxyFactory(orderRepository);
    factory.addAdvisor(getAdvisor(logTrace));
    OrderRepositoryV1 proxy = (OrderRepositoryV1) factory.getProxy();

    log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderRepository.getClass());

    return proxy;
  }

  private Advisor getAdvisor(LogTrace logTrace) {
    // pointcut
    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedNames("request*", "order*", "save*");

    return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(logTrace));
  }

}
