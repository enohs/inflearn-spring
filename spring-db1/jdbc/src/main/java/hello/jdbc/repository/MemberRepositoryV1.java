package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * JDBC - DataSource 사용, JdbcUtils 사용
 */
@Slf4j
public class MemberRepositoryV1 {

  private final DataSource dataSource;

  public MemberRepositoryV1(DataSource dataSource) {
    this.dataSource = dataSource;
  }

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

  // 귀찮은 close 처리를 JdbcUtils를 사용하여 해결할 수 있다!
  private void close(Connection con, Statement stmt, ResultSet rs) {
    JdbcUtils.closeResultSet(rs);
    JdbcUtils.closeStatement(stmt);
    JdbcUtils.closeConnection(con);
  }

  // 이제는 DataSource에서 Connection을 가져오도록 변경
  private Connection getConnection() throws SQLException {
    Connection con = dataSource.getConnection();
    log.info("get connection={}, class={}", con, con.getClass());
    return con;
  }

}
