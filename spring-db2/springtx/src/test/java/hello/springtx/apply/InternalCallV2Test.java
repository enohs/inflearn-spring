package hello.springtx.apply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Internal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV2Test {


  @Autowired
  CallService callService;

  @Test
  void printProxy() {
    log.info("callService class={}", callService.getClass());
  }

  @Test
  void externalCall() {
    callService.external();
  }

  @Test
  void externalCallV2() {
    callService.external();
  }

  @TestConfiguration
  static class InternalCallV1TestConfig {

    @Bean
    InternalService internalService() {
      return new InternalService();
    }

    @Bean
    CallService callService() {
      return new CallService(internalService());
    }
  }

  @RequiredArgsConstructor
  static class CallService {

    // 이 안에 주입되는 객체는 프록시 객체 -> 트랜잭션을 적용할 수 있음
    private final InternalService internalService;

    public void external() {
      log.info("call external");
      printTxInfo();
      internalService.internal();
    }

    private void printTxInfo() {
      boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
      log.info("tx active={}", txActive);
    }
  }

  static class InternalService {

    @Transactional
    public void internal() {
      log.info("call internal");
      printTxInfo();
    }

    private void printTxInfo() {
      boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
      log.info("tx active={}", txActive);
    }
  }

}
