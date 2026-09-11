package hello.springbootautoconfig.selector;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

public class ImportSelectorTest {

  // config를 사용해서 설정
  @Test
  void staticConfig() {
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(StaticConfig.class);
    HelloBean bean = appContext.getBean(HelloBean.class);
    assertThat(bean).isNotNull();
  }

  // selector를 사용해서 설정
  @Test
  void selectorConfig() {
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(SelectorConfig.class);
    HelloBean bean = appContext.getBean(HelloBean.class);
    assertThat(bean).isNotNull();
  }

  // import된 config 가져다 사용. 이미 값이 정해져서 동적으로 변경하기 어려움
  @Configuration
  @Import(HelloConfig.class)
  public static class StaticConfig {

  }

  // selector를 임포트하면 selector의 메서드를 돌려 나온 결과를 설정 정보로 사용함
  // selector 내부에 String으로 클래스를 작성하기 때문에 동적으로 설정이 가능해짐
  @Configuration
  @Import(HelloImportSelector.class)
  public static class SelectorConfig {

  }

}
