package hello.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Slf4j
public class MemoryCondition implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    // -Dmemory=on <- 해당 설정은 IDE의 configuration의 VM 설정을 통해 전달
    String memory = context.getEnvironment().getProperty("memory");
    log.info("memory={}", memory);
    return "on".equals(memory);
  }
}
