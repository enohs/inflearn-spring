package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;

/**
 * JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV0 {

  public Member save(Member member) throws SQLException {
    String sql = "insert into member(member_id, money) values (?, ?)";

    Connection con = null;
    PreparedStatement pstmt = null;

    try {
      con = getConnection();
      pstmt = con.prepareStatement(sql);
      // sql문의 ?에 값을 채워넣는 부분
      pstmt.setString(1, member.getMemberId());
      pstmt.setInt(2, member.getMoney());
      // sql 데이터베이스에 실행
      pstmt.executeUpdate();
      return member;
    } catch (SQLException e) {
      log.error("db error", e);
      throw e;
    } finally {
      // 연결 종료 필수
      // 근데 종료 시에 에러 발생할 수 있어서 추가조치 함
      // 다만 이미 닫는 부분에서 오류가 생기게 되면 딱히 해줄 수 있는 게 없어서 로그만 남기기로 함
      close(con, pstmt, null);
    }
  }

  public Member findById(String memberId) throws SQLException {
    String sql = "select * from member where member_id = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = getConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, memberId);

      rs = pstmt.executeQuery();
      // 처음에는 데이터를 가리키지 않고 있기 때문에 한 번 호출을 해서 데이터 위치로 커서를 옮기는 것
      if (rs.next()) {
        Member member = new Member();
        member.setMemberId(rs.getString("member_id"));
        member.setMoney(rs.getInt("money"));
        return member;
      } else {
        throw new NoSuchElementException("member not found memberId = " + memberId);
      }

    } catch (SQLException e) {
      log.error("db error", e);
      throw e;
    } finally {
      close(con, pstmt, rs);
    }
  }

  public void update(String memberId, int money) throws SQLException {
    String sql = "update member set money=? where member_id=?";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = getConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, money);
      pstmt.setString(2, memberId);
      int resultSize = pstmt.executeUpdate();
      log.info("resultSize={}", resultSize);

    } catch (SQLException e) {
      log.error("db error", e);
      throw e;
    } finally {
      close(con, pstmt, rs);
    }
  }

  public void delete(String memberId) throws SQLException {
    String sql = "delete from member where member_id=?";

    Connection con = null;
    PreparedStatement pstmt = null;

    try {
      con = getConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, memberId);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      log.error("db error", e);
      throw e;
    } finally {
      close(con, pstmt, null);
    }
  }

  // Statement는 sql 쿼리를 그래로 넣는 것이고, PrepareStatement는 파라미터 전달가능
  private void close(Connection con, Statement stmt, ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        log.info("error", e);
      }
    }

    if (stmt != null) {
      try {
        stmt.close();
      } catch (SQLException e) {
        log.info("error", e);
      }
    }

    if (con != null) {
      try {
        con.close();
      } catch (SQLException e) {
        log.info("error", e);
      }
    }

  }

  private Connection getConnection() {
    return DBConnectionUtil.getConnection();
  }

}
