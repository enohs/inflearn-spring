package hello.login.web.filter;

import hello.login.web.SessionConst;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

@Slf4j
public class LoginCheckFilter implements Filter {

  private static final String[] whiteList = {"/", "/login", "/members/add", "/logout", "/css/*", "/.well-known/*"};

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String requestURI = httpRequest.getRequestURI();

    HttpServletResponse httpResponse = (HttpServletResponse) response;

    try {
      log.info("인증 체크 필터 시작 {}", requestURI);

      if (isLoginCheckPath(requestURI)) {
        log.info("인증 체크 로직 실행 {}", requestURI);
        HttpSession session = httpRequest.getSession(false);

        // 관련된 세션이 없는 경우 -> 미인증 사용자 요청
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
          log.info("미인증 사용자 요청 {}", requestURI);
          // 로그인으로 리다이렉트
          httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
          return; // 회원이 아닌 접근이라면 다음 필터로 안 가고 바로 넘어가겠다는 뜻 -> 리다이렉트 URL이 호출됨
        }
      }
      chain.doFilter(request, response);
    } catch (Exception e) {
      throw e; // 예외 로깅 가능하지만, 톰캣까지 예외를 보내줘야 정상 처리됨
    } finally {
      log.info("인증 체크 필터 종료 {}", requestURI);
    }

  }

  /**
   * 화이트 리스트의 경우 인증 체크 X
   */
  private boolean isLoginCheckPath(String requestURI) {
    // URI가 리스트 안에 있는 경로랑 매칭이 되는지 확인해 주는 유틸
    return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
  }
}
