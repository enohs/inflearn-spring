package hello.springadv1.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jspecify.annotations.Nullable;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

  @Override
  public @Nullable Object invoke(MethodInvocation invocation) throws Throwable {
    log.info("TimeProxy 실행");
    long startTime = System.currentTimeMillis();

    // Object result = method.invoke(target, args);
    // 타겟은 Proxy Factory가 MethodInvocation 안에 넣어줌
    Object result = invocation.proceed(); // 알아서 타겟을 찾아 호출해줌

    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;
    log.info("TImeProxy 종료 resultTime={}", resultTime);
    return result;
  }
}
