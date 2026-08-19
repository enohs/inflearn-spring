package hello.springadv1.aop.proxyvs;

import hello.springadv1.aop.member.MemberService;
import hello.springadv1.aop.member.MemberServiceImpl;
import hello.springadv1.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
//@SpringBootTest(properties = "spring.aop.proxy-target-class=false") // 동적 프록시
@SpringBootTest(properties = "spring.aop.proxy-target-class=true") // CGLIB
@Import(ProxyDIAspect.class)
public class ProxyDITest {

  @Autowired
  MemberService memberService;
  // 인터페이스가 존재하기 때문에 JDK 동적 프록시의 경우 프록시가 생성되긴 하지만 프록시가 구체 클래스를 알 수 없어서 타입 문제가 발생한다.
  @Autowired
  MemberServiceImpl memberServiceImpl;

  @Test
  void go() {
    log.info("memberService class={}", memberService.getClass());
    log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
    memberService.hello("helloA");
  }
}
