package hello.springadv1.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BasicPostProcessorTest {

  @Test
  void basicConfig() {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicPostProcessorConfig.class); // <- 스프링 컨테이너

    // beanA 이름으로 B 객체가 빈으로 등록된다.
    B beanA = applicationContext.getBean("beanA", B.class);
    beanA.helloB();

    // A는 빈으로 등록되지 않음
    Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(A.class));
  }

  @Slf4j
  @Configuration
  static class BasicPostProcessorConfig {

    @Bean(name = "beanA")
    public A a() {
      return new A();
    }

    // 빈 등록만 하면 스프링이 알아서 프로세서 적용해줌
    @Bean
    public BeanPostProcessor aToBPostProcessor() {
      return new AToBPostProcessor();
    }
  }

  @Slf4j
  static class A {

    public void helloA() {
      log.info("hello A");
    }
  }

  @Slf4j
  static class B {

    public void helloB() {
      log.info("hello B");
    }
  }

  @Slf4j
  static class AToBPostProcessor implements BeanPostProcessor {

    // @PostConstructor 이전에 동작
    // 빈의 초기화 작업이 시작되기 전 설정을 변경하거나 검증할 때 사용
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
      return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    // @PostConstructor 이후에 동작
    // 초기화가 완료된 빈을 바탕으로 프록시 객체를 생성하거나 최종 조작을 할 때 주로 사용
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
      log.info("beanName={} bean={}", beanName, bean);
      if (bean instanceof A) { // 빈 바꿔치기
        return new B();
      }
      return bean;
    }
  }
}
