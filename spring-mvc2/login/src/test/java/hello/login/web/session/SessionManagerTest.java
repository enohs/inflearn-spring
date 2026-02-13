package hello.login.web.session;

import static org.assertj.core.api.Assertions.assertThat;

import hello.login.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class SessionManagerTest {

  SessionManager sessionManager = new SessionManager();

  @Test
  void sessionTest() {

    // 테스트를 위해 적절한 HttpServletResponse 구현체를 가져오는 게 까다로움 -> 테스트를 위한 Mock response 구현체가 있다!
    MockHttpServletResponse response = new MockHttpServletResponse();

    // 세션 생성
    // 서버에서 웹 브라우저로 쿠키를 처음 보내는 것을 시뮬
    Member member = new Member();
    sessionManager.createSession(member, response);

    // 요청에 응답 쿠키 저장 확인
    // 웹 브라우저가 요청에 쿠키를 넣어 보내는 것을 시뮬
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(response.getCookies());

    // 세션 조회
    Object result = sessionManager.getSession(request);
    assertThat(result).isEqualTo(member);

    // 세션 만료
    sessionManager.expire(request);
    Object expired = sessionManager.getSession(request);
    assertThat(expired).isNull();
  }

}