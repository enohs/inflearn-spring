package hello.jdbc.exception.Basic;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class UncheckedTest {

  @Test
  void unchecked_catch() {
    Service service = new Service();
    service.callCatch();
  }

  @Test
  void unchecked_throe() {
    Service service = new Service();
    assertThatThrownBy(() -> service.callThrow())
        .isInstanceOf(MyUncheckedException.class);
  }

  /**
   * RuntimeException을 상속받은 예외는 언체크 예외가 된다
   */
  static class MyUncheckedException extends RuntimeException {

    public MyUncheckedException(String message) {
      super(message);
    }
  }

  /**
   * Unchecked 예외는 예외는 잡거나 던지지 않아도 된다
   * <p>
   * 예외를 잡지 않으면 자동으로 던져진다
   */
  static class Service {

    Repository repository = new Repository();

    /**
     * 필요한 경우 예외를 잡아서 처리하면 된다
     */
    public void callCatch() {
      try {
        repository.call();
      } catch (MyUncheckedException e) {
        log.info("예외 처리, message={}", e.getMessage(), e);
      }
    }

    /**
     * 예외 잡지 않아도 된다
     */
    public void callThrow() {
      repository.call();
    }
  }

  static class Repository {

    // 런타임 예외는 throws 없어도 된다!
    public void call() {
      throw new MyUncheckedException("ex");
    }
  }
}
