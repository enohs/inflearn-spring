package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentResolver.Login;
import hello.login.web.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

  private final MemberRepository memberRepository;
  private final SessionManager sessionManager;

  //@GetMapping("/")
  public String home() {
    return "home";
  }

  // @CookieValue를 사용하면 타입 변환도 자동으로 해줌.
  // required가 false여야 비로그인 회원도 접근 가능
  //@GetMapping("/")
  public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
    if (memberId == null) {
      return "home";
    }

    // 로그인
    Member loginMember = memberRepository.findById(memberId);
    if (loginMember == null) {
      return "home";
    }

    model.addAttribute("member", loginMember);
    return "loginHome";
  }

  // 직접 만든 세션 도입
  //@GetMapping("/")
  public String homeLoginV2(HttpServletRequest request, Model model) {
    Member member = (Member) sessionManager.getSession(request);

    // 로그인
    if (member == null) {
      return "home";
    }

    model.addAttribute("member", member);
    return "loginHome";
  }

  //@GetMapping("/")
  public String homeLoginV3(HttpServletRequest request, Model model) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      return "home";
    }

    Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

    // 세션에 회원 데이터가 없으면 home
    if (loginMember == null) {
      return "home";
    }

    // 세션이 유지되면 로그인으로 이동
    model.addAttribute("member", loginMember);
    return "loginHome";
  }

  // @SessionAttribute 도입
  // 세션을 생성하지는 않음
  //@GetMapping("/")
  public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

    // 세션에 회원 데이터가 없으면 home
    if (loginMember == null) {
      return "home";
    }

    // 세션이 유지되면 로그인으로 이동
    model.addAttribute("member", loginMember);
    return "loginHome";
  }

  // argument resolver 직접 활용
  @GetMapping("/")
  public String homeLoginV3ArgumentResolver(@Login Member loginMember, Model model) {

    // 세션에 회원 데이터가 없으면 home
    if (loginMember == null) {
      return "home";
    }

    // 세션이 유지되면 로그인으로 이동
    model.addAttribute("member", loginMember);
    return "loginHome";
  }
}