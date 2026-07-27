package hello.springadv1.proxy.cglib;

import hello.springadv1.proxy.cglib.code.TimeMethodInterceptor;
import hello.springadv1.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

  @Test
  void cglib() {
    ConcreteService target = new ConcreteService();

    Enhancer enhancer = new Enhancer(); // cglib 만드는 코드
    enhancer.setSuperclass(ConcreteService.class); // 지금은 구체 클래스 기반이기 때문에 구체 클래스를 상속받은 프록시를 생성해야 함
    enhancer.setCallback(new TimeMethodInterceptor(target));

    ConcreteService proxy = (ConcreteService) enhancer.create(); // 프록시 생성

    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass());

    proxy.call();
  }

}
