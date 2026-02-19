package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberServiceV1 {

  private final MemberRepositoryV1 memberRepository;

  public void accountTransfer(String fromId, String toId, int money) throws SQLException {
    // 트랜잭션 시작
    Member fromMember = memberRepository.findById(fromId);
    Member toMember = memberRepository.findById(toId);

    memberRepository.update(fromId, fromMember.getMoney() - money);
    validation(toMember); // 예외 발생 로직
    memberRepository.update(toId, toMember.getMoney() + money);
    // 커밋 또는 롤백 결정
  }

  private static void validation(Member toMember) {
    if (toMember.getMemberId().equals("ex")) {
      throw new IllegalStateException("이체 중 예외 발생");
    }
  }
}
