package hello.springbootprojectv1.config;

import hello.memory.MemoryController;
import hello.memory.MemoryFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

  @Bean
  public MemoryFinder memoryFinder() {
    return new MemoryFinder();
  }

  @Bean
  public MemoryController memoryController() {
    return new MemoryController(memoryFinder());
  }

}
