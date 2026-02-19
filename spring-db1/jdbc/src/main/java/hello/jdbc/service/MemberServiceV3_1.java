package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_1 {

  //private final DataSource dataSource; // Jdbc 의존성이 문제가 되기 때문에 인터페이스에 의존하는 것으로 변경
  private final PlatformTransactionManager transactionManager;
  private final MemberRepositoryV3 memberRepository;

  public void accountTransfer(String fromId, String toId, int money) throws SQLException {
    // 트랜잭션 시작
    // TransactionStatus 안에는 트랜잭션 상태 정보가 들어 있다. 커밋, 롤백 시 필요함
    // getTransaction에 옵션이 있는데 나중에 나옴
    TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

    try {
      // 비즈니스 로직, 트랜잭션 시작
      bizLogic(fromId, toId, money);
      transactionManager.commit(status); // 성공 시 커밋
    } catch (Exception e) {
      transactionManager.rollback(status); // 실패 시 롤백
      throw new IllegalStateException(e);
    } // 만약 에러가 발생해도 트랜잭션 매니저가 전부 닫아준다
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

  private void bizLogic(String fromId, String toId, int money) throws SQLException {
    Member fromMember = memberRepository.findById(fromId);
    Member toMember = memberRepository.findById(toId);

    memberRepository.update(fromId, fromMember.getMoney() - money);
    validation(toMember); // 예외 발생 로직
    memberRepository.update(toId, toMember.getMoney() + money);
  }
}
