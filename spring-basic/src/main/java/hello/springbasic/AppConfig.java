package hello.springbasic;

import hello.springbasic.discount.DiscountPolicy;
import hello.springbasic.discount.RateDiscountPolicy;
import hello.springbasic.member.MemberRepository;
import hello.springbasic.member.MemberService;
import hello.springbasic.member.MemberServiceImpl;
import hello.springbasic.member.MemoryMemberRepository;
import hello.springbasic.order.OrderService;
import hello.springbasic.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 어플리케이션 설정 정보에 적어주는 어노테이션
// 각 메서드가 반환하는 객체가 스프링 컨테이너에 등록되어 관리된다. 이때 등록된 객체를 스프링 빈이라고 함
@Configuration
public class AppConfig {

  // AppConfig의 등장으로 사용 영역과 구성 영역이 분리가 됨
  // AppConfig는 구성 영역
  @Bean // 빈 어노테이션을 통해 각각의 메서드가 스프링 컨테이너에 등록이 됨
  public MemberService memberService() {
    System.out.println("call AppConfig.memberService"); // 예상 1번, 실제 1번
    return new MemberServiceImpl(memberRepository());
  }

  // 빈 이름은 중복되지 않게 주의해야 함
  @Bean // 이름도 바꿀 수 있는데 관례상 메서드 이름을 빈 이름으로 둔다 (이름 변경x)
  public OrderService orderService() {
    System.out.println("call AppConfig.orderService"); // 예상 1번, 실제 1번
    return new OrderServiceImpl(memberRepository(), discountPolicy());
  }

  @Bean
  public MemberRepository memberRepository() {
    System.out.println("call AppConfig.memberRepository"); // 예상 3번, 실제 1번
    return new MemoryMemberRepository();
  }

  // 이제 바꿔끼우기 가능!!
  @Bean
  public DiscountPolicy discountPolicy() {
    //return new FixDiscountPolicy();
    return new RateDiscountPolicy();
  }
}
