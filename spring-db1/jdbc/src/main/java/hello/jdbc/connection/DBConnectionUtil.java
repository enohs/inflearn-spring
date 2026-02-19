package hello.jdbc.connection;

import static hello.jdbc.connection.ConnectionConst.PASSWORD;
import static hello.jdbc.connection.ConnectionConst.URL;
import static hello.jdbc.connection.ConnectionConst.USERNAME;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBConnectionUtil {

  // 여기서 사용된 Connection이 jdbc에서 지원하는 connection을 의미함
  public static Connection getConnection() {
    try {
      // 라이브러리에서 적절한 드라이버를 찾아서 꺼냄
      Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      log.info("get connection={}, class={}", connection, connection.getClass());
      return connection;
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

}
