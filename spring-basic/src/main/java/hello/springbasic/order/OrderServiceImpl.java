package hello.springbasic.order;

import hello.springbasic.annotation.MainDiscountPolicy;
import hello.springbasic.discount.DiscountPolicy;
import hello.springbasic.member.Member;
import hello.springbasic.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

  // 구체적인 클래스에 대한 정보는 전혀 없고 추상에 대한 정보만 있음
  // -> DIP 야무지게 지킴. 이제 인터페이스만 보고 코드 작성하면 됨
  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy;

  /* 일반 메서드 주입 (사용 안 함)
  // 한 번에 여러 필드 주입 가능하긴 한데 setter나 생성자 주입 쓰면 됨
  @Autowired
  public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }
  */

  /* 이 방식은 필드 주입 (사용 안 함. 스프링 사용하는 테스트 코드에서나 사용하는 편)
  // -> 코드는 간결한데 의존성을 직접 넣을 방법이 없기 때문에 자바로 테스트할 방법이 없음 (null 포인트 인젝션 발생)
  // setter를 생성하면 해결할 수야 있겠지만 굳이...
  // 스프링을 쓰면 생성자, setter 없이도 주입 가능
  @Autowired private MemberRepository memberRepository;
  @Autowired private DiscountPolicy discountPolicy;
  */

  /* 이런 방식이 setter 주입 -> 선택적이고 변경 가능성이 있는 의존관계에 사용
  // 생성자 없어도 주입 가능.
  @Autowired(required = false) // 디폴트는 주입할 대상 없으면 오류. 주입할 대상 없어도 동작하게 하려면 false로 지정
  public void setDiscountPolicy(DiscountPolicy discountPolicy) {
    this.discountPolicy = discountPolicy;
  }
  */

  // 생성자가 하나일 때는 @Autowired 생략이 가능하다
  // 생성자 주입은 처음 실행 시 1번만 일어나며, 불변과 필수 의존관계 형성을 위해 사용한다
  // 원래라면 빈 등록과 의존관계 주입이 서로 따로 진행되지만 생성자 주입은 자바 코드 특성상 빈 등록 시 같이 의존관계가 주입된다
  // @Autowired는 스프링 컨테이너가 관리하는 스프링 빈이어야만 동작함 (@Component 없으면 의미 없는 어노테이션이 된다는 뜻)

  // @Autowired는 타입을 우선으로 매핑하는데 만약 같은 타입이 여러 개라면 변수명과 똑같은 빈 이름을 찾아 매핑시킨다
  @Autowired
  public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
    System.out.println("memberRepository = " + memberRepository);
    System.out.println("discountPolicy = " + discountPolicy);
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member findMember = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(findMember, itemPrice);

    return new Order(memberId, itemName, itemPrice, discountPrice);
  }

  // 테스트 용도
  public MemberRepository getMemberRepository() {
    return memberRepository;
  }
}
