package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;
  private final SessionManager sessionManager;

  @GetMapping("/login")
  public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
    return "login/loginForm";
  }

  //@PostMapping("/login")
  public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
    if (bindingResult.hasErrors()) {
      return "login/loginForm";
    }

    Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

    if (loginMember == null) {
      bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
      return "login/loginForm";
    }

    // 로그인 성공 시, 상태 유지를 위한 쿠키 생성 및 전달
    // 쿠키에 시간 정보를 주지 않으면 세션 쿠키 (브라우저 종료 시 같이 만료)
    Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
    response.addCookie(idCookie);

    return "redirect:/";
  }

  // 직접 만든 세션 도입
  //@PostMapping("/login")
  public String loginV2(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
    if (bindingResult.hasErrors()) {
      return "login/loginForm";
    }

    Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

    if (loginMember == null) {
      bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
      return "login/loginForm";
    }

    // 로그인 성공 시, 상태 유지를 위한 쿠키 생성 및 전달
    // 세션 관리자를 통해 세션을 생성하고, 회원 데이터 보관
    sessionManager.createSession(loginMember, response);

    return "redirect:/";
  }

  // 서블릿 HTTP 세션 활용
  //@PostMapping("/login")
  public String loginV3(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
    if (bindingResult.hasErrors()) {
      return "login/loginForm";
    }

    Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

    if (loginMember == null) {
      bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
      return "login/loginForm";
    }

    // 로그인 성공
    // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성해서 반환
    // getSession(true)가 디폴트. false로 하면 세션이 없을 때 새로운 세션을 생성하지 않음 (true는 새 세션 생성)
    HttpSession session = request.getSession();
    // 세션에 로그인 회원 정보를 보관
    session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

    return "redirect:/";
  }

  @PostMapping("/login")
  public String loginV4(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request, @RequestParam(defaultValue = "/") String redirectURL) {
    if (bindingResult.hasErrors()) {
      return "login/loginForm";
    }

    Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

    if (loginMember == null) {
      bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
      return "login/loginForm";
    }

    // 로그인 성공
    HttpSession session = request.getSession();
    // 세션에 로그인 회원 정보를 보관
    session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

    // /login?redirectURL=/items 이라는 URL에서 POST하기 때문에 쿼리 파라미터를 그대로 활용하는 방식
    // 이전에 아이템으로 바로 가려고 했을 때 로그인 페이지로 튕기면, 로그인 시 바로 아이템 화면이 보이도록 유도
    // 이게 가능한 이유는 타임리프에서 th:action을 사용해서 현재 URL을 가지고 요청을 보내기 때문
    return "redirect:" + redirectURL;
  }

  // 쿠키 지우는 방법은 그냥 만료 시간을 0으로 해주면 됨
  //@PostMapping("/logout")
  public String logout(HttpServletResponse response) {
    expireCookie(response, "memberId");
    return "redirect:/";
  }

  // 직접 만든 세션 도입
  //@PostMapping("/logout")
  public String logoutV2(HttpServletRequest request) {
    sessionManager.expire(request);
    return "redirect:/";
  }

  // 서블릿 HTTP 세션 활용
  @PostMapping("/logout")
  public String logoutV3(HttpServletRequest request) {
    HttpSession session = request.getSession(false);

    if (session == null) {
      session.invalidate(); // 세션 날리기
    }

    return "redirect:/";
  }

  private static void expireCookie(HttpServletResponse response, String cookieName) {
    Cookie cookie = new Cookie(cookieName, null);
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }
}
