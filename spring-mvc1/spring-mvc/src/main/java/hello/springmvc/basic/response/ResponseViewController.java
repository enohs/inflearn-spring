package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

  @RequestMapping("/response-view-v1")
  public ModelAndView responseViewV1() {
    ModelAndView mav = new ModelAndView("response/hello")
        .addObject("data", "hello!");

    return mav;
  }

  @RequestMapping("/response-view-v2")
  public String responseViewV2(Model model) {
    model.addAttribute("data", "hello!");
    // @Controller에 String 반환하면 View의 논리적 이름이 된다!
    // @ResponseBody를 쓰면 뷰를 찾지 않고 그냥 메시지 바디로 출력됨
    return "response/hello";
  }

  // 컨트롤러 경로와 뷰의 논리적 이름이 같을 때 반환하는 게 없다면 컨트롤러 경로를 뷰의 논리적 경로로 자동 매핑됨 (권장하지 않음)
  @RequestMapping("/response/hello")
  public void responseViewV3(Model model) {
    model.addAttribute("data", "hello!");
  }
}
