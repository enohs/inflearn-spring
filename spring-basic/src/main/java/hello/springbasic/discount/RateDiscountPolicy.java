package hello.springbasic.discount;

import hello.springbasic.annotation.MainDiscountPolicy;
import hello.springbasic.member.Grade;
import hello.springbasic.member.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Qualifier("mainDiscountPolicy") // 빈 이름을 바꾸는 게 아니라 부가적인 정보 제공. 생성자나 필드 앞에 @Qualifier("mainDiscountPolicy")를 붙이면 알아서 인식함
// 만약에 mainDiscountPolicy를 못 찾으면 같은 이름의 스프링 빈을 찾는다

//@Primary // 이거 하나 있으면 표시할 거 없이 바로 Autowired 시 다른 기본 타입보다 우선순위를 가짐
// 다만 @Qualifier보다는 우선순위가 낮기 때문에 둘을 같이 혼용해서 쓰면 적절하게 매핑할 수 있다

@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {

  private int discountPercent = 10;

  @Override
  public int discount(Member member, int price) {
    if (member.getGrade() == Grade.VIP) {
      return price * discountPercent / 100;
    } else {
      return 0;
    }
  }
}
