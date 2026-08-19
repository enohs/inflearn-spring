package hello.springadv1.aop.proxyvs;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hello.springadv1.aop.member.MemberService;
import hello.springadv1.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

  @Test
  void jdkProxy() {
    MemberServiceImpl target = new MemberServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(false); // jdk 동적 프록시

    // 프록시를 인터페이스로 캐스팅 성공
    MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

    // JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패. ClassCastException 예외 발생
    // 현재 캐스팅의 대상은 프록시인제 프록시는 구현체를 알 수 없기 때문
    assertThrows(ClassCastException.class, () -> {
      MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    });
  }

  @Test
  void cglibProxy() {
    MemberServiceImpl target = new MemberServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(true); // cglib

    // 프록시를 인터페이스로 캐스팅 성공
    MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

    log.info("proxy class={}", memberServiceProxy.getClass());

    // CGLIB를 구현 클래스로 캐스팅 시도 성공.
    // CGLIB는 구체 클래스로 프록시를 만들기 때문에 구현 클래스로 캐스팅이 가능
    assertThatCode(() -> {
      MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }).doesNotThrowAnyException();
  }
}
