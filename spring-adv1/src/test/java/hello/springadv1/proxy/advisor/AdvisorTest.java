package hello.springadv1.proxy.advisor;

import hello.springadv1.proxy.common.advice.TimeAdvice;
import hello.springadv1.proxy.common.service.ServiceImpl;
import hello.springadv1.proxy.common.service.ServiceInterface;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

@Slf4j
public class AdvisorTest {

  @Test
  void advisorTest1() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);

    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());// 기본적으로 사용되는 어드바이저. Pointcut.TRUE는 모든 코드에 참으로 적용됨을 의미

    proxyFactory.addAdvisor(advisor);
    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    proxy.save();
    proxy.find();
  }

  @Test
  @DisplayName("직접 만든 포인트컷")
  void advisorTest2() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);

    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());

    proxyFactory.addAdvisor(advisor);
    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    proxy.save();
    proxy.find();
  }

  @Test
  @DisplayName("스프링이 제공하는 포인트컷")
  void advisorTest3() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);

    // 실무에서는 AspectJExpressionPointcut을 가장 많이 사용
    // 이건 이름 기반으로 메서드 필터링해주는 포인트컷
    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedNames("save");

    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());

    proxyFactory.addAdvisor(advisor);
    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    proxy.save();
    proxy.find();
  }

  // 아래 두 필터가 모두 true여야 advice가 적용됨
  static class MyPointcut implements Pointcut {

    @Override
    public ClassFilter getClassFilter() {
      return ClassFilter.TRUE; // 우리는 메서드만 따지기 때문에 항상 통과하게 설정
    }

    @Override
    public MethodMatcher getMethodMatcher() {
      return new MyMethodMatcher();
    }
  }

  static class MyMethodMatcher implements MethodMatcher {

    private String matchName = "save";

    // Runtime false일 때 호출
    // 정적인 정보를 사용하여 캐싱이 가능
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
      boolean result = method.getName().equals(matchName);

      log.info("포인트컷 호출 method={} targetClass={}", method.getName(), targetClass);
      log.info("포인트컷 결과 result={}", result);

      return result;
    }

    // 이번 경우 아래 두 메서드는 패스
    @Override
    public boolean isRuntime() {
      return false;
    }

    // Runtime true일 때 호출
    // 인수 넘어오는 건 캐싱이 어려움
    @Override
    public boolean matches(Method method, Class<?> targetClass, @Nullable Object... args) {
      return false;
    }
  }
}
