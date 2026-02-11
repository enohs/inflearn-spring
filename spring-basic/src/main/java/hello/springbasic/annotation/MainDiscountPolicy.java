package hello.springbasic.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.beans.factory.annotation.Qualifier;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited // 어떤 클래스가 현재 어노테이션이 붙은 상태로 자식 클래스에 상속되면 이 어노테이션의 기능이 상속됨을 의미
@Documented
@Qualifier("mainDiscountPolicy") // 문자 오타를 컴파일 시점에 확인하기 위한 예제
public @interface MainDiscountPolicy {


}
