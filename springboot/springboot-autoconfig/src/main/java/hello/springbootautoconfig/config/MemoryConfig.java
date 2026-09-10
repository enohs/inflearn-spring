package hello.springbootautoconfig.config;

import hello.memory.MemoryController;
import hello.memory.MemoryFinder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// memory 패키지가 라이브러리라고 생각했을 때, 라이브러리 사용을 위한 configuration
// 자동 구성이 아닌 수동 구성
@Configuration
//@Conditional(MemoryCondition.class) // 해당 클래스의 메서드 반환 값에 따라 실행 여부 결정 가능
@ConditionalOnProperty(name = "memory", havingValue = "on") // 위에 작성한 어노테이션과 같은 기능. 스프링에서 미리 Condition 인터페이스를 구현해놓고, 그 값만 전달하는 방식. 여기서 MemoryCondition은 사용되지 않음
public class MemoryConfig {

  @Bean
  public MemoryController memoryController() {
    return new MemoryController(memoryFinder());
  }

  @Bean
  public MemoryFinder memoryFinder() {
    return new MemoryFinder();
  }
}
