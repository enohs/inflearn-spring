package hello.springadv1.proxy.config.v6_aop;

import hello.springadv1.proxy.config.AppV1Config;
import hello.springadv1.proxy.config.AppV2Config;
import hello.springadv1.proxy.config.v6_aop.aspect.LogTraceAspect;
import hello.springadv1.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AopConfig {

  @Bean
  public LogTraceAspect logTraceAspect(LogTrace logTrace) {
    return new LogTraceAspect(logTrace); // 어드바이저 등록하는 것과 같은 개념
  }
}
