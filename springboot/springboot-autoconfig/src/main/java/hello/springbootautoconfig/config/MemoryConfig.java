package hello.springbootautoconfig.config;

import hello.memory.MemoryController;
import hello.memory.MemoryFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// memory 패키지가 라이브러리라고 생각했을 때, 라이브러리 사용을 위한 configuration
// 자동 구성이 아닌 수동 구성
@Configuration
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
