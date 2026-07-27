package hello.springadv1.proxy.cglib.code;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

  private final Object target;

  public TimeMethodInterceptor(Object target) {
    this.target = target;
  }

  @Override
  public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
    log.info("TimeProxy 실행");
    long startTime = System.currentTimeMillis();

    Object result = proxy.invoke(target, args); // Method보다 MethodProxy가 조금 더 빠르다고는 함

    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;
    log.info("TImeProxy 종료 resultTime={}", resultTime);
    return result;
  }
}
