package hello.springbasic.scope;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

  @Test
  void prototypeBeanFind() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

    System.out.println("find prototypeBean1");
    PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class); // 프로토타입은 호출할 때 생성이 됨
    System.out.println("find prototypeBean2");
    PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
    System.out.println("prototypeBean1 = " + prototypeBean1);
    System.out.println("prototypeBean2 = " + prototypeBean2);

    assertThat(prototypeBean1).isNotSameAs(prototypeBean2);
    ac.close(); // 프로토타입은 destroy 메서드가 호출되지 않음 (스프링 컨테이너의 관리 범위에서 벗어났기 때문)
    // 객체 생성, 의존성 주입, 초기화까지만 스프링 컨테이너가 관여.
    // 싱글톤과 달리 호출마다 매번 새로운 객체 생성 후 반환

    prototypeBean1.destroy(); // 직접 종료
    prototypeBean2.destroy();
  }

  @Scope("prototype")
  static class PrototypeBean{

    @PostConstruct
    public void init() {
      System.out.println("PrototypeBean.init");
    }

    @PreDestroy
    public void destroy() {
      System.out.println("PrototypeBean.destroy");
    }
  }

}
