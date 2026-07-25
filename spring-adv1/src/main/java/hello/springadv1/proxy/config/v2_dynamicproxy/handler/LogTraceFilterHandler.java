package hello.springadv1.proxy.config.v2_dynamicproxy.handler;

import hello.springadv1.proxy.trace.TraceStatus;
import hello.springadv1.proxy.trace.logtrace.LogTrace;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.springframework.util.PatternMatchUtils;

public class LogTraceFilterHandler implements InvocationHandler {

  private final Object target;
  private final LogTrace logTrace;
  private final String[] patterns;

  public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
    this.target = target;
    this.logTrace = logTrace;
    this.patterns = patterns;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    // 메서드 이름 필터
    String methodName = method.getName();
    // save, request, reque*, *est ...
    if (!PatternMatchUtils.simpleMatch(patterns, methodName)) {
      return method.invoke(target, args);
    }

    TraceStatus status = null;
    try {
      // "OrderController.request()" 같은 문장 만들기 위한 용도
      String message = method.getDeclaringClass().getSimpleName() + "." + methodName + "()";
      status = logTrace.begin(message);

      // 로직 호출
      Object result = method.invoke(target, args);

      logTrace.end(status);
      return result;
    } catch (Exception e) {
      logTrace.exception(status, e);
      throw e;
    }
  }
}
