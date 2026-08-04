package hello.springadv1.proxy.config.v3_proxyfactory;

import hello.springadv1.proxy.app.v2.OrderControllerV2;
import hello.springadv1.proxy.app.v2.OrderRepositoryV2;
import hello.springadv1.proxy.app.v2.OrderServiceV2;
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
public class ProxyFactoryConfigV2 {

  @Bean
  public OrderControllerV2 orderControllerV2Proxy(LogTrace logTrace) {
    OrderControllerV2 orderController = new OrderControllerV2(orderServiceV2Proxy(logTrace));

    ProxyFactory factory = new ProxyFactory(orderController);
    factory.addAdvisor(getAdvisor(logTrace));
    OrderControllerV2 proxy = (OrderControllerV2) factory.getProxy();

    log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderController.getClass());

    return proxy;
  }

  @Bean
  public OrderServiceV2 orderServiceV2Proxy(LogTrace logTrace) {
    OrderServiceV2 orderService = new OrderServiceV2(orderRepositoryV2Proxy(logTrace));

    ProxyFactory factory = new ProxyFactory(orderService);
    factory.addAdvisor(getAdvisor(logTrace));
    OrderServiceV2 proxy = (OrderServiceV2) factory.getProxy();

    log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderService.getClass());

    return proxy;
  }

  @Bean
  public OrderRepositoryV2 orderRepositoryV2Proxy(LogTrace logTrace) {
    OrderRepositoryV2 orderRepository = new OrderRepositoryV2();

    ProxyFactory factory = new ProxyFactory(orderRepository);
    factory.addAdvisor(getAdvisor(logTrace));
    OrderRepositoryV2 proxy = (OrderRepositoryV2) factory.getProxy();

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
