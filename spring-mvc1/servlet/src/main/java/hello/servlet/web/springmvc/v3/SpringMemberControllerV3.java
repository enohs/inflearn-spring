package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

  private MemberRepository memberRepository = MemberRepository.getInstance();

  //@RequestMapping(value = "/new-form", method = RequestMethod.GET)
  @GetMapping("/new-form")
  public String newForm() {
    return "new-form";
  }

  //@RequestMapping(value = "/save", method = RequestMethod.POST)
  @PostMapping("/save")
  // HttpRequestServlet 말고 직접 파라미터를 받아올 수도 있다! 타입 변환도 자동으로 가능
  // Model도 입력 받아서 모델 안에 값을 넣어주면 뷰로 데이터 전송도 됨!
  // ModelAndView 말고 String을 반환해도 알아서 적용한다
  public String save(
      @RequestParam("username") String username,
      @RequestParam("age") int age,
      Model model) {
    Member member = new Member(username, age);
    memberRepository.save(member);

    model.addAttribute("member", member);
    return "save-result";
  }

  //@RequestMapping(method = RequestMethod.GET)
  @GetMapping
  public String members(Model model) {
    List<Member> members = memberRepository.findAll();
    model.addAttribute("members", members);

    return "members";
  }

}
