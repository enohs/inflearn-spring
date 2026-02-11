package hello.servlet.web.springmvc.old;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

// 스프링 mvc에서는 스프링 빈의 이름으로 핸들러를 찾도록 매핑할 수 있다
// 핸들러 어댑터의 경우 대부분 스프링에서 지원함
@Component("/springmvc/old-controller")
public class OldController implements Controller {

  @Override
  public @Nullable ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    System.out.println("OldController.handleRequest");
    return new ModelAndView("new-form");
  }
}
