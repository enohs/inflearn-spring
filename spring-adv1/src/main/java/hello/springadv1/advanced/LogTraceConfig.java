package hello.springadv1.advanced;

import hello.springadv1.advanced.trace.logtrace.LogTrace;
import hello.springadv1.advanced.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

  @Bean
  public LogTrace logTrace() {
    return new ThreadLocalLogTrace();
  }

}
