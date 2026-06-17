package hello.springadv1;

import hello.springadv1.trace.logtrace.FieldLogTrace;
import hello.springadv1.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

  @Bean
  public LogTrace logTrace() {
    return new FieldLogTrace(); // 싱글톤으로 사용이 되면 필드는~?
  }

}
