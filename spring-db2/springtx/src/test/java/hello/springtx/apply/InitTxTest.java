package hello.springtx.apply;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
public class InitTxTest {

  @Autowired
  Hello hello;

  @Test
  void go() {
    // 초기화 코드(@PostConstruct)는 스프링이 초기화 시점에 호출한다.(직접 호출 안 하는 게 맞음)
    // 그런데 초기화가 먼저 다 진행이 된 다음에 트랜잭션이 시작되기 때문에 Transactional이 적용이 안 됨

    // 그런데 EventListener를 사용하면 초기화가 다 된 다음에 실행할 수 있어서 트랜잭션 적용이 가능하다
  }


  @TestConfiguration
  static class InitTxTestConfig {

    @Bean
    Hello hello() {
      return new Hello();
    }
  }

  @Slf4j
  static class Hello {

    @PostConstruct
    @Transactional
    public void initV1() {
      boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
      log.info("Hello init @PostConstruct tx active={}", isActive);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initV2() {
      boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
      log.info("Hello init ApplicationReadyEvent tx active={}", isActive);
    }
  }

}
