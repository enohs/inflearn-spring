package hello.springbasic.scan.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.context.annotation.ComponentScan.Filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class ComponentFilterAppConfigTest {

  @Test
  void filterScan() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

    BeanA beanA = ac.getBean("beanA", BeanA.class);
    assertThat(beanA).isNotNull();

    assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("beanB", BeanB.class));
  }


  @Configuration
  @ComponentScan(
      // FilterType.ANNOTATION: 어노테이션 인식. 기본값
      // FilterType.ASSIGNABLE_TYPE: 지정한 타입과 자식 타입을 인식해서 동작 -> 어노테이션이 아닌 클래스를 명시할 때 사용 가능
      // FilterType.ASPECTJ: AspectJ 패턴 사용
      // FilterType.REGEX: 정규 표현식
      // FilterType.CUSTOM: TypeFilter라는 인터페이스를 구현해서 처리
      includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class), // 스캔 추가
      excludeFilters = @Filter(classes = MyExcludeComponent.class) // 스캔 제외
  )
  static class ComponentFilterAppConfig {

  }

}
