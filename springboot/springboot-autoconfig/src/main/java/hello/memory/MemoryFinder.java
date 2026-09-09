package hello.memory;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemoryFinder {

  public Memory get() {
    // JVM에서 메모리 정보를 실시간 조회하는 기능
    long max = Runtime.getRuntime().maxMemory();
    long total = Runtime.getRuntime().totalMemory();
    long free = Runtime.getRuntime().freeMemory();
    long used = total - free;

    return new Memory(used, max);
  }

  @PostConstruct
  public void init() {
    log.info("init memoryFinder");
  }

}
