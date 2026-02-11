package hello.springbasic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// @Component가 붙은 클래스를 스캔해서 스프링 빈으로 등록한다 (@Controller, @Service, @Repository, @Configuration도 추가로 스캔한다. 내부에 Component가 들어있기 때문)
// 참고로 어노테이션 내부에 특정 어노테이션이 있다고 그걸 인식하는 건 자바가 아닌 스프링이 지원하는 기술이다. 어노테이션은 상속도 안 되기 때문에 스프링에서 지원
// @Component를 찾아 돌면서 스프링 빈으로 만들 때에는 빈의 이름을 메서드가 아닌 클래스명으로 사용함 (앞글자는 소문자로)
// @Component는 구현체에 붙인다
@ComponentScan(
    // basePackages와 basePackageClasses를 지정하지 않으면 현재 스캔이 붙어있는 클래스부터 시작해서 하위 패키지를 전부 뒤짐 -> 지금은 hello.springbasic 패키지에 있어서 프로젝트만 다 뒤지게 됨
    // -> 패키지 위치를 지정하지 않고 설정 정보 클래스의 위치만 프로젝트 최상단에 두는 방식 권장 (디폴트 설정 사용)
    // 사실 SpringBasicApplication 클래스에서 스캔을 하긴 한다. 위의 원리 사용. 그래서 별도로 스캔을 돌릴 필요가 없긴 하다.

    // 스캔 할 패키지를 표시해줌. {}로 여러 개 명시 가능
    basePackages = "hello.springbasic",
    // 지정한 클래스의 패키지를 탐색 위치로 함
    basePackageClasses = AutoAppConfig.class,
    // 기존 AppConfig는 스캔에서 제외하기 위한 설정
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
  // @Bean으로 등록한 게 하나도 없음

  // 만약 자동 빈끼리 같은 이름이 존재한다면 컴파일 에러가 발생하며
  // 만약 수동 빈과 자동 빈이 충돌하면 수동 빈으로 오버라이딩 되어 덮어씌워진다
  // 그런데 수동 빈과 자동 빈이 충돌하는 경우는 의도보다는 버그인 경우가 많아서 버그 잡기가 어려워진다 -> 스프링 부트에서는 이제 충돌이 나도록 기본값을 수정했다.
}

