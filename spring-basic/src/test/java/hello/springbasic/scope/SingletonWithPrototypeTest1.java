package hello.springbasic.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeTest1 {

  @Test
  void prototypeFind() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

    PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
    prototypeBean1.addCount();
    Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);

    PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
    prototypeBean2.addCount();
    Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);
  }

  @Test
  void singletonClientUsePrototype() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

    ClientBean clientBean1 = ac.getBean(ClientBean.class);
    ClientBean clientBean2 = ac.getBean(ClientBean.class);
    // 싱글톤 내부에서 프로토타입의 메서드를 호출하는데, 이때 싱글톤 안의 프로토타입은 싱글톤 생성 시점에 이미 스프링 컨테이너에서 생성되어 초기화까지 마친 상태
    // -> 프로토타입을 관리하는 권한은 완전히 싱글톤에게 넘어가 있어서 프로토타입의 메서드를 호출하더라도 동일한 프로토타입만을 사용한다
    int count1 = clientBean1.logic();
    int count2 = clientBean2.logic();

    Assertions.assertThat(count1).isEqualTo(1);
    Assertions.assertThat(count2).isEqualTo(1);
  }

  @Scope("prototype")
  static class PrototypeBean {

    private int count = 0;

    public void addCount() {
      count++;
    }

    public int getCount() {
      return count;
    }

    @PostConstruct
    public void init() {
      System.out.println("PrototypeBean.init " + this);
    }

    @PreDestroy
    public void destroy() {
      System.out.println("PrototypeBean.destroy");
    }
  }

  @Scope("singleton")
  static class ClientBean {

    //private final PrototypeBean prototypeBean; // 스프링 빈 생성 시점에 이미 주입 완료
    //@Autowired
//    public ClientBean(PrototypeBean prototypeBean) {
//      this.prototypeBean = prototypeBean;
//    }

    // 스프링이 자동으로 빈으로 만들어준 거 사용
    // 스프링 코드에 의존하긴 함 (그래도 다른 컨테이너 쓸 예정이 아니라면 권장)
    //@Autowired
    //private ObjectProvider<PrototypeBean> prototypeBeanProvider;

    // 자바 표준을 사용한 방식. 허나 다른 컨테이너를 사용해야할 게 아니라면 ObjectProvider 사용 권장
    @Autowired
    private Provider<PrototypeBean> prototypeBeanProvider;

    // 만약 프로토타입의 메서드를 호출할 때마다 프로토타입을 새로 만들기 원한다면 생성자를 통해 주입받지 말고 직접 ApplicationContext에서 그때그때 만드는 방법도 있음 (좋은 방법은 아님)
    public int logic() {
      // ObjectProvider와 Provider가 DL (Dependency Lookup)을 해줌
      // ApplicationContext로 빈을 찾아주는 역할만 대신 해줌
      PrototypeBean prototypeBean = prototypeBeanProvider.get();
      prototypeBean.addCount();
      return prototypeBean.getCount();
    }
  }

}
