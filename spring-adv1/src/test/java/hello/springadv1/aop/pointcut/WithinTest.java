package hello.springadv1.aop.pointcut;

import static org.assertj.core.api.Assertions.assertThat;

import hello.springadv1.aop.member.MemberServiceImpl;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class WithinTest {

  AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
  Method helloMethod;

  @BeforeEach
  public void init() throws NoSuchMethodException {
    helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
  }

  // within보다는 execution을 더 잘 사용함
  @Test
  void withinExact() {
    pointcut.setExpression("within(hello.springadv1.aop.member.MemberServiceImpl)");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void withinStar() {
    pointcut.setExpression("within(hello.springadv1.aop.member.*Service*)");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void withinSubPackage() {
    pointcut.setExpression("within(hello.springadv1..*)");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  // 표현식에 부모 타입을 지정할 수 없다. 타입이 완전히 동일해야 사용 가능
  @Test
  @DisplayName("타겟의 타입에만 직접 적용, 인터페이스를 선정하면 안 됨")
  void withSuperTypeFalse() {
    pointcut.setExpression("within(hello.springadv1.aop.member.MemberService)");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
  }

  @Test
  @DisplayName("execution은 타입 기반, 인터페이스 선정 가능")
  void executionSuperTypeTrue() {
    pointcut.setExpression("execution(* hello.springadv1.aop.member.MemberService.*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }
}
