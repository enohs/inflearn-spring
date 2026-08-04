package hello.springadv1.proxy.config.v4_postprocessor;

import hello.springadv1.proxy.config.AppV1Config;
import hello.springadv1.proxy.config.AppV2Config;
import hello.springadv1.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.springadv1.proxy.config.v4_postprocessor.postprocessor.PackageLogTracePostProcessor;
import hello.springadv1.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class BeanPostProcessorConfig {

  @Bean
  public PackageLogTracePostProcessor packageLogTracePostProcessor(LogTrace logTrace) {
    return new PackageLogTracePostProcessor("hello.springadv1.proxy.app", getAdvisor(logTrace));
  }

  private Advisor getAdvisor(LogTrace logTrace) {
    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedNames("request*", "order*", "save*");

    return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(logTrace));
  }

}
