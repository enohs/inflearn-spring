package hello.jdbc.exception.Basic;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class CheckedTest {

  @Test
  void checked_catch() {
    Service service = new Service();
    service.callCatch();
  }

  @Test
  void checked_throw() {
    Service service = new Service();
    assertThatThrownBy(() -> service.callThrow()).isInstanceOf(MyCheckedException.class);
  }

  /**
   * Exception을 상속받은 예외는 체크 예외가 된다
   */
  static class MyCheckedException extends Exception {

    public MyCheckedException(String message) {
      super(message);
    }
  }

  /**
   * Checked 예외는 예외를 처리하거나, 던지거나 둘 중 하나를 필수로 선택
   */
  static class Service {

    Repository repository = new Repository();

    /**
     * 예외를 잡아서 처리하는 코드
     */
    public void callCatch() {
      try {
        repository.call();
      } catch (MyCheckedException e) {
        log.info("예외 처리, message={}", e.getMessage(), e);
      }
    }

    /**
     * 체크 예외를 밖으로 던지는 코드. throws 선언 필수
     *
     * @throws MyCheckedException
     */
    public void callThrow() throws MyCheckedException {
      repository.call();
    }
  }

  static class Repository {

    // 체크 예외는 잡거나, 명시적으로 던져야 한다
    public void call() throws MyCheckedException {
      throw new MyCheckedException("ex");
    }
  }

}
