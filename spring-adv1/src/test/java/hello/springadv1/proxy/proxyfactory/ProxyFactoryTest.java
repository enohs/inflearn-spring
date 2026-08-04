package hello.springadv1.proxy.proxyfactory;

import hello.springadv1.proxy.common.advice.TimeAdvice;
import hello.springadv1.proxy.common.service.ServiceImpl;
import hello.springadv1.proxy.common.service.ServiceInterface;
import java.security.Provider.Service;
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
}
