package hello.springbasic.member;


import hello.springbasic.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
  // 테스트 파일은 빌드되어 배포되지는 않는다

  MemberService memberService;

  // 테스트 코드 실행 전에 우선적으로 실행할 코드를 작성 (생성자 느낌인 듯)
  // 태스트가 2개 있으면 2번 도는 방식
  @BeforeEach
  public void beforeEach() {
    AppConfig appConfig = new AppConfig();
    memberService = appConfig.memberService();
  }

  @Test
  void join() {
    // given
    Member member = new Member(1L, "memberA", Grade.VIP);

    // when
    memberService.join(member);
    Member findMember = memberService.findMember(1L);

    // then
    Assertions.assertThat(member).isEqualTo(findMember);
  }
}
