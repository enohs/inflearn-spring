package hello.springadv1.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

  // hello.springadv1.aop.order 패키지와 하위 패키지
  // 포인트컷 시그니처 -> 재사용도 가능
  // 반환타입은 void 고정, 코드 내용은 비워둠
  @Pointcut("execution(* hello.springadv1.aop.order..*(..))")
  private void allOrder() {

  }

  @Around("allOrder()")
  public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
    return joinPoint.proceed();
  }

}
