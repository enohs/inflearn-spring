package hello.springadv1.proxy.proxyfactory;

import hello.springadv1.proxy.common.advice.TimeAdvice;
import hello.springadv1.proxy.common.service.ConcreteService;
import hello.springadv1.proxy.common.service.ServiceImpl;
import hello.springadv1.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

@Slf4j
public class ProxyFactoryTest {

  @Test
  @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
  void interfaceProxy() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.addAdvice(new TimeAdvice());
    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass());

    proxy.save();

    Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue(); // 직접 프록시를 만들면 작동 안 하고, ProxyFactory를 쓸 때만 사용가능한 유틸
    Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue(); // 직접 프록시를 만들면 작동 안 하고, ProxyFactory를 쓸 때만 사용가능한 유틸
    Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isFalse(); // 직접 프록시를 만들면 작동 안 하고, ProxyFactory를 쓸 때만 사용가능한 유틸
  }

  @Test
  @DisplayName("인터페이스가 없으면 CGLIB 사용")
  void concreteProxy() {
    ConcreteService target = new ConcreteService();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.addAdvice(new TimeAdvice());
    ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass());

    proxy.call();

    Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue(); // 직접 프록시를 만들면 작동 안 하고, ProxyFactory를 쓸 때만 사용가능한 유틸
    Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse(); // 직접 프록시를 만들면 작동 안 하고, ProxyFactory를 쓸 때만 사용가능한 유틸
    Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue(); // 직접 프록시를 만들면 작동 안 하고, ProxyFactory를 쓸 때만 사용가능한 유틸
  }

  // 스프링 부트는 AOP를 적용할 때 항상 아래 옵션을 true로 설정하여 CGLIB를 사용함
  @Test
  @DisplayName("proxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고 클래스 기반 프록시 사")
  void proxyTargetClass() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);

    proxyFactory.setProxyTargetClass(true); // <- CGLIB 전환 코드

    proxyFactory.addAdvice(new TimeAdvice());
    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass());

    proxy.save();

    Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
    Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
  }
}
