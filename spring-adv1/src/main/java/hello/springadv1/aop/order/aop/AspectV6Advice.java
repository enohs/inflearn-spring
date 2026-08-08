package hello.springadv1.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class AspectV6Advice {

  // 실행 순서는 @Around, @Before, @After, @AfterReturning, @AfterThrowing 순서임
  // (하지만 돌아오는 순서도 존재하기 때문에 결과적으로는 @Around, @Before, @AfterThrowing, @AfterReturning, @After)
  // @Around 외의 어드바이스가 존재하는 이유는 단순함과 타겟 자동 실행의 이점, 작성 의도가 명확히 드러나기 때문

  // 유일하게 ProceedingJoinPoint만 사용 가능 -> 타겟 호출이 수동임
  // JoinPoint의 실행 여부를 결정할 수 있음
  // 실행되는 원본 메서드에 다른 파라미터값을 넣을 수 있음
  // 반환값 변환 가능
  // 예외 변환 가능
  // proceed()를 여러 번 호출 가능
  @Around("hello.springadv1.aop.order.aop.Pointcuts.orderAndService()")
  public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
    try {
      // @Before
      log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
      Object result = joinPoint.proceed();
      // @AfterReturning
      log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
      return result;
    } catch (Exception e) {
      // @AfterThrowing
      log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
      throw e;
    } finally {
      // @After
      log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
    }
  }

  // 이후 조인 포인트 실행은 알아서 해줌
  // ProceedingJoinPoint가 아닌 JoinPoint를 사용 (파라미터 없이도 가능)
  @Before("hello.springadv1.aop.order.aop.Pointcuts.orderAndService()")
  public void doBefore(JoinPoint joinPoint) {

    log.info("[before] {}", joinPoint.getSignature());
  }

  // 반환될 값의 변수명은 명시해줘야 하고, 파라미터에서 같은 이름을 써야 함
  // 반환값을 조작할 수는 있지만 임의로 변경은 불가능함
  // 반환값의 타입이 포인트컷에 걸리는 메서드의 반환타입과 일치하거나 그 부모타입이어야 어드바이스가 실행됨
  @AfterReturning(value = "hello.springadv1.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
  public void doReturn(JoinPoint joinPoint, Object result) {
    log.info("[return] {} return={}", joinPoint.getSignature(), result);
  }

  // 여기서는 예외가 자동으로 throw 됨
  // AfterReturning과 마찬가지로 throwing 속성값과 매개변수가 같아야 함
  // 예외의 타입도 포인트컷 대상과 같거나 부모타입이어야 동작함
  @AfterThrowing(value = "hello.springadv1.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
  public void doThrowing(JoinPoint joinPoint, Exception ex) {
    log.info("[ex] {} message={}", joinPoint.getSignature(), ex);
  }

  @After(value = "hello.springadv1.aop.order.aop.Pointcuts.orderAndService()")
  public void doAfter(JoinPoint joinPoint) {
    log.info("[after] {}", joinPoint.getSignature());
  }
}
