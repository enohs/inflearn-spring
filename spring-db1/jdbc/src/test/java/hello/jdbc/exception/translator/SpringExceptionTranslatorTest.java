package hello.jdbc.exception.translator;

import static hello.jdbc.connection.ConnectionConst.PASSWORD;
import static hello.jdbc.connection.ConnectionConst.URL;
import static hello.jdbc.connection.ConnectionConst.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

@Slf4j
public class SpringExceptionTranslatorTest {

  DataSource dataSource;

  @BeforeEach
  void init() {
    dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
  }

  @Test
  void sqlExceptionErrorCode() {
    String sql = "select bad grammer";

    try {
      Connection con = dataSource.getConnection();
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.executeQuery();
    } catch (SQLException e) {
      assertThat(e.getErrorCode()).isEqualTo(42122);
      int errorCode = e.getErrorCode();
      log.info("errorCode={}", errorCode);
      log.info("error", e);
    }
  }

  @Test
  void exceptionTranslator() {
    String sql = "select bad grammar";

    try {
      Connection con = dataSource.getConnection();
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.executeQuery();
    } catch (SQLException e) {
      assertThat(e.getErrorCode()).isEqualTo(42122);

      // 변환기를 통해 다양한 종로의 데이터 접근 관련 에러를 처리해줄 수 있다
      // org.springframework.jdbc.support.sql-error-codes.xml이라는 파일에 여러 에러 코드들이 정리되어 있어서 가능한 처리
      SQLErrorCodeSQLExceptionTranslator exceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
      DataAccessException resultEx = exceptionTranslator.translate("select", sql, e);
      log.info("resultEx", resultEx);

      // 스프링이 변환기를 통해 특정 기술에 종속적이지 않은 예외를 직접 변환해준다
      // 스프링에 대한 기술 종속성까지 감안하기는 현실적이진 않기에 어쩔 수 없다고 봄
      assertThat(resultEx.getClass()).isEqualTo(BadSqlGrammarException.class);
    }
  }

}
