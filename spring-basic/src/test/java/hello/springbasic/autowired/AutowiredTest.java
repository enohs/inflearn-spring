package hello.springbasic.autowired;

import hello.springbasic.member.Member;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

public class AutowiredTest {

  @Test
  void autowiredOption() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);


  }


  static class TestBean {

    // false 설정 시 파라미터 대상에 대한 의존관계가 없으면 그냥 setter 호출이 안 됨
    @Autowired(required = false)
    public void setNoBean1(Member member) {
      System.out.println("member1 = " + member);
    }

    // 호출은 되는데 null값이 들어옴
    // @Nullable은 실제로 생성자에서 특정 값만 선택적으로 받을 때 사용할 수도 있음
    @Autowired
    public void setNoBean2(@Nullable Member member) {
      System.out.println("member2 = " + member);
    }

    // Optional이라는 특수성 때문에 wrapping 되어 들어옴. 파라미터로 Optional은 비추
    @Autowired
    public void setNoBean3(Optional<Member> memberOptional) {
      System.out.println("memberOptional = " + memberOptional);
    }
  }

}
