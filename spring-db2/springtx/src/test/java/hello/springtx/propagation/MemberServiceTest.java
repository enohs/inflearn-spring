package hello.springtx.propagation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

@Slf4j
@SpringBootTest
class MemberServiceTest {

  @Autowired
  MemberService memberService;
  @Autowired
  MemberRepository memberRepository;
  @Autowired
  LogRepository logRepository;

  /**
   * memberService    @Transactional: OFF
   * <p>
   * memberRepository @Transactional: ON
   * <p>
   * logRepository    @Transactional: ON
   */
  @Test
  void outerTxOff_success() {
    // given
    String username = "outerTxOff_success";

    //when
    memberService.joinV1(username);

    //then
    assertTrue(memberRepository.find(username).isPresent());
    assertTrue(logRepository.find(username).isPresent());
  }

  /**
   * memberService    @Transactional: OFF
   * <p>
   * memberRepository @Transactional: ON
   * <p>
   * logRepository    @Transactional: ON / Exception
   */
  @Test
  void outerTxOff_fail() {
    // given
    String username = "로그예외_outerTxOff_fail";

    //when
    assertThatThrownBy(() -> memberService.joinV1(username))
        .isInstanceOf(RuntimeException.class);

    //then
    assertTrue(memberRepository.find(username).isPresent());
    assertTrue(logRepository.find(username).isEmpty());
  }

  /**
   * memberService    @Transactional: On
   * <p>
   * memberRepository @Transactional: OFF
   * <p>
   * logRepository    @Transactional: OFF
   */
  @Test
  void singleTx() {
    // given
    String username = "outerTxOff_success";

    //when
    memberService.joinV1(username);

    //then
    assertTrue(memberRepository.find(username).isPresent());
    assertTrue(logRepository.find(username).isPresent());
  }

  /**
   * memberService    @Transactional: On
   * <p>
   * memberRepository @Transactional: ON
   * <p>
   * logRepository    @Transactional: ON
   */
  @Test
  void outerTxOn_success() {
    // given
    String username = "outerTxOff_success";

    //when
    memberService.joinV1(username);

    //then : 성공적으로 커밋
    assertTrue(memberRepository.find(username).isPresent());
    assertTrue(logRepository.find(username).isPresent());
  }

  /**
   * memberService    @Transactional: On
   * <p>
   * memberRepository @Transactional: ON
   * <p>
   * logRepository    @Transactional: ON / Exception
   */
  @Test
  void outerTxOn_fail() {
    // given
    String username = "로그예외_outerTxOff_fail";

    //when : 물리 트랜잭션으로 묶이면 둘 중 하나라도 롤백할 때 모두 롤백해야 한다
    // 근데 지금은 예외를 안 잡았기 때문에 서비스에서도 예외가 터진 것. -> 신규 트랜잭션에서 롤백하기 때문에 그냥 전체 롤백임
    // 그리고 그 예외가 클라이언트까지 와서 에러가 터진 것
    assertThatThrownBy(() -> memberService.joinV1(username))
        .isInstanceOf(RuntimeException.class);

    //then
    assertTrue(memberRepository.find(username).isEmpty());
    assertTrue(logRepository.find(username).isEmpty());
  }

  /**
   * memberService    @Transactional: On
   * <p>
   * memberRepository @Transactional: ON
   * <p>
   * logRepository    @Transactional: ON / Exception
   */
  @Test
  void recoverException_fail() {
    // given
    String username = "로그예외_outerTxOff_fail";

    //when : 레포지토리에 있는 논리 트랜잭션에서 예외가 발생했을 때 rollback only를 트랜잭션 매니저로 설정함
    // 그러면 서비스에서 예외처리를 하더라도 커밋할 때 롤백이 수행됨
    assertThatThrownBy(() -> memberService.joinV2(username))
        .isInstanceOf(UnexpectedRollbackException.class);

    //then : 유저 정보는 저장하고 싶었지만 내부 트랜잭션에서 예외가 발생해서 롤백되었고, 정보가 저장이 안 됨
    assertTrue(memberRepository.find(username).isEmpty());
    assertTrue(logRepository.find(username).isEmpty());
  }

  /**
   * memberService    @Transactional: On
   * <p>
   * memberRepository @Transactional: ON
   * <p>
   * logRepository    @Transactional: ON(REQUIRES_NEW) / Exception
   */
  @Test
  void recoverException_success() {
    // given
    String username = "로그예외_outerTxOff_success";

    //when : 아예 별도의 커넥션을 만들도록 설정하여 일정 부분 독립적으로 운용
    memberService.joinV2(username);

    //then : member 저장, log 롤백
    assertTrue(memberRepository.find(username).isPresent());
    assertTrue(logRepository.find(username).isEmpty());
  }

}