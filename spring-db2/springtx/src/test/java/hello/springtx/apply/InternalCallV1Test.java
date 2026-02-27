package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV1Test {


  @Autowired
  CallService callService;

  @Test
  void printProxy() {
    log.info("callService class={}", callService.getClass());
  }

  @Test
  void internalCall() {
    callService.internal();
  }

  @Test
  void externalCall() {
    callService.external();
  }

  @TestConfiguration
  static class InternalCallV1TestConfig {

    @Bean
    CallService callService() {
      return new CallService();
    }
  }

  static class CallService {

    public void external() {
      log.info("call external");
      printTxInfo();
      // 트랜잭션이 적용된 메서드를 서비스 안에서 직접 호출한다면?
      // 프록시에서 메서드를 실행하는 게 아니라 현재 인스턴스에서 메서드를 실행하기 때문에 트랜잭션이 적용될 수가 없다
      internal();
    }

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
