package hello.springbasic.scan.filter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 어떤 대상 위에 붙일 수 있게 할 것인가 (Type은 클래스 레벨)
@Retention(RetentionPolicy.RUNTIME) // 어노테이션 생명주기
@Documented // 문서화 여부
public @interface MyIncludeComponent {

}
