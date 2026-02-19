package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 트랜잭션 - 파라미터 연동, 풀을 고려한 종료
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

  private final MemberRepositoryV2 memberRepository;
  private final DataSource dataSource;

  public void accountTransfer(String fromId, String toId, int money) throws SQLException {
    Connection con = dataSource.getConnection();
    try {
      con.setAutoCommit(false); // 트랜잭션 시작
      // 비즈니스 로직, 트랜잭션 시작
      bizLogic(con, fromId, toId, money);
      // 커밋 또는 롤백 결정
      con.commit(); // 성공 시 커밋
    } catch (Exception e) {
      con.rollback(); // 실패 시 롤백
      throw new IllegalStateException(e);
    } finally {
      if (con != null) {
        release(con);
      }
    }
  }

  private static void validation(Member toMember) {
    if (toMember.getMemberId().equals("ex")) {
      throw new IllegalStateException("이체 중 예외 발생");
    }
  }

  private static void release(Connection con) {
    try {
      con.setAutoCommit(true); // 커넥션 풀 고려
      con.close(); // 자동으로 커넥션 풀에 반납이 됨 -> 다른 사용자가 자동 밋으로 쓸 수 있도록 원상복구
    } catch (Exception e) {
      log.info("error", e);
    }
  }

  private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
    Member fromMember = memberRepository.findById(con, fromId);
    Member toMember = memberRepository.findById(con, toId);

    memberRepository.update(con, fromId, fromMember.getMoney() - money);
    validation(toMember); // 예외 발생 로직
    memberRepository.update(con, toId, toMember.getMoney() + money);
  }
}
