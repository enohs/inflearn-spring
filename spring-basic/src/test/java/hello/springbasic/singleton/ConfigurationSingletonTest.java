package hello.springbasic.singleton;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import hello.springbasic.AppConfig;
import hello.springbasic.member.MemberRepository;
import hello.springbasic.member.MemberServiceImpl;
import hello.springbasic.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

  @Test
  void configurationTest() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
    OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
    MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

    MemberRepository memberRepository1 = memberService.getMemberRepository();
    MemberRepository memberRepository2 = orderService.getMemberRepository();

    System.out.println("memberService -> memberRepository1 = " + memberRepository1);
    System.out.println("orderService -> memberRepository2 = " + memberRepository2);
    System.out.println("memberRepository -> memberRepository = " + memberRepository);
    // 분명 AppConfig에서는 new를 여러번 호출하는데 실제로는 다 같은 참조 값을 가짐!!
    //MemoryMemberRepository@12365c88
    //MemoryMemberRepository@12365c88
    //MemoryMemberRepository@12365c88
    // @Configuration 덕분에 싱글톤이 되었기 때문

    assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
    assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
  }

  @Test
  void configurationDeep() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    AppConfig bean = ac.getBean(AppConfig.class);
    System.out.println("bean = " + bean.getClass()); // AppConfig$$SpringCGLIB$$0
    // 실제 내가 만든 클래스가 아니라 스프링이 CGLIB라는 바이트 코드 조작 라이브러리를 통해 AppConfig 클래스를 상속받는 임의의 클래스를 만든 후, 그 클래스를 스프링 빈으로 등록한 것!
    // AppConfig의 자식이기에 AppConfig.class로 조회해도 출력된 것

    // 만약 AppConfig에서 @Configuration을 사용하지 않으면, 스프링 빈에는 정상적으로 등록이 된다.
    // 다만, CGLIB을 생성하지 않아 싱글톤 보장이 안 된다.
    // 실제로 없이 테스트하면 순수한 AppConfig 정보가 출력되며 memberRepository도 여러 번 호출된다
  }

}
