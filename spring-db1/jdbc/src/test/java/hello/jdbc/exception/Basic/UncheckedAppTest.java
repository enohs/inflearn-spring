package hello.jdbc.exception.Basic;

import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class UncheckedAppTest {

  // 시스템에서 발생한 예외는 대부분 바로 복구 불가능한 예외이기 때문에
  // 런타임 예외를 사용하면 서비스나 컨트롤러가 예외를 신경쓰지 않아도 된다

  // 이전 로직들에서는 의존관계 필요 없고,
  // 예외 공통 처리 부분에서 한 번에 처리 가능
  @Test
  void checked() {
    Controller controller = new Controller();
    Assertions.assertThatThrownBy(controller::request)
        .isInstanceOf(RuntimeSQLException.class);
  }

  @Test
  void printEx() {
    Controller controller = new Controller();
    try {
      controller.request();
    } catch (Exception e) {
      log.info("ex", e);
    }
  }

  static class Controller {

    Service service = new Service();

    public void request() {
      service.logic();
    }
  }

  static class Service {

    Repository repository = new Repository();
    NetWorkClient netWorkClient = new NetWorkClient();

    public void logic() {
      repository.call();
      netWorkClient.call();
    }
  }

  static class NetWorkClient {

    public void call() {
      throw new RuntimeConnectException("연결 실패");
    }
  }

  static class Repository {

    public void call() {
      // 체크 예외를 런타임 예외로 변환
      try {
        runSQL();
      } catch (SQLException e) {
        throw new RuntimeSQLException(e);
      }
    }

    public void runSQL() throws SQLException {
      throw new SQLException("ex");
    }
  }

  static class RuntimeConnectException extends RuntimeException {

    public RuntimeConnectException(String message) {
      super(message);
    }
  }

  static class RuntimeSQLException extends RuntimeException {

    public RuntimeSQLException() {
    }

    public RuntimeSQLException(String message) {
      super(message);
    }

    public RuntimeSQLException(Throwable cause) {
      super(cause);
    }
  }

}
