package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 트랜잭션 - 트랜잭션 템플릿
 */
@Slf4j
public class MemberServiceV3_2 {

  //private final PlatformTransactionManager transactionManager;
  private final TransactionTemplate txTemplate;
  private final MemberRepositoryV3 memberRepository;

  public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepository) {
    // 굳이 TransactionTemplate의 의존성을 직접 받지 않는 이유는 TransactionTemplate이 클래스라 유연성이 떨어지기 때문 (관례이기도 하다)
    this.txTemplate = new TransactionTemplate(transactionManager);
    this.memberRepository = memberRepository;
  }

  public void accountTransfer(String fromId, String toId, int money) throws SQLException {
    txTemplate.executeWithoutResult((status) -> {
      try {
        bizLogic(fromId, toId, money);
      } catch (SQLException e) {
        throw new IllegalStateException(e);
      }
    });
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
