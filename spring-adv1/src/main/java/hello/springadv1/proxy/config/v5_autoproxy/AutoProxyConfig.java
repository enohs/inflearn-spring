package hello.springadv1.proxy.config.v5_autoproxy;

import hello.springadv1.proxy.config.AppV1Config;
import hello.springadv1.proxy.config.AppV2Config;
import hello.springadv1.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.springadv1.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

  // 스프링의 자동 프록시 생성기는 이미 등록되어 있기 때문에 어드바이저만 빈 등록하면 자동으로 프록시 적용이 됨
  //@Bean
  public Advisor advisor1(LogTrace logTrace) {
    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedNames("request*", "order*", "save*");

    return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(logTrace));
  }

  //@Bean
  public Advisor advisor2(LogTrace logTrace) {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression("execution(* hello.springadv1.proxy.app..*(..))");

    return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(logTrace));
  }

  @Bean
  public Advisor advisor3(LogTrace logTrace) {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression("execution(* hello.springadv1.proxy.app..*(..)) && !execution(* hello.springadv1.proxy.app..noLog(..))");

    return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(logTrace));
  }
}
