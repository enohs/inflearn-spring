package hello.springadv1.aop.pointcut;

import static org.assertj.core.api.Assertions.assertThat;

import hello.springadv1.aop.member.MemberServiceImpl;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class ArgsTest {

  Method helloMethod;

  @BeforeEach
  public void init() throws NoSuchMethodException {
    helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
  }

  private AspectJExpressionPointcut pointcut(String expression) {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression(expression);
    return pointcut;
  }

  @Test
  void args() {
    // hello(String)과 매칭
    assertThat(pointcut("args(String)")
        .matches(helloMethod, MemberServiceImpl.class)).isTrue();
    // args에서는 파라미터의 타입으로 부모 타입을 입력 가능
    // execution에서는 불가
    assertThat(pointcut("args(Object)")
        .matches(helloMethod, MemberServiceImpl.class)).isTrue();
    assertThat(pointcut("args()")
        .matches(helloMethod, MemberServiceImpl.class)).isFalse();
    assertThat(pointcut("args(..)")
        .matches(helloMethod, MemberServiceImpl.class)).isTrue();
    assertThat(pointcut("args(*)")
        .matches(helloMethod, MemberServiceImpl.class)).isTrue();
    assertThat(pointcut("args(String, ..)")
        .matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  /**
   * execution(* *(java.io.Serializable)): 메서드의 시그니처로 판단 (정적)
   * <p>
   * args(java.io.Serializable): 런타임에 전달된 인수로 판단 (동적)
   */
  @Test
  void argsVsExecution() {
    // Args
    assertThat(pointcut("args(String)")
        .matches(helloMethod, MemberServiceImpl.class)).isTrue();
    assertThat(pointcut("args(java.io.Serializable)")
        .matches(helloMethod, MemberServiceImpl.class)).isTrue();
    assertThat(pointcut("args(Object)")
        .matches(helloMethod, MemberServiceImpl.class)).isTrue();

    // Execution
    assertThat(pointcut("execution(* *(String))")
        .matches(helloMethod, MemberServiceImpl.class)).isTrue();
    // 매칭 실패
    assertThat(pointcut("execution(* *(java.io.Serializable))")
        .matches(helloMethod, MemberServiceImpl.class)).isFalse();
    // 매칭 실패
    assertThat(pointcut("execution(* *(Object))")
        .matches(helloMethod, MemberServiceImpl.class)).isFalse();
  }
}
