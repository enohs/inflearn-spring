package hello.login.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

  public static final String LOG_ID = "logId";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String requestURI = request.getRequestURI();
    String uuid = UUID.randomUUID().toString();

    // afterCompletion으로 인자를 넘기고 싶을 때 멤버 변수 설정할 경우, 싱글톤으로 관리되어 상태 유지 불가함 -> setAttribute를 사용해서 request에 담아 넘길 수 있음
    request.setAttribute(LOG_ID, uuid);

    // @RequestMapping을 사용하면 HandlerMethod가 사용됨
    // @RequestMapping: HandlerMethod
    // 정적 리소스: ResourceHttpRequestHandler
    if (handler instanceof HandlerMethod) {
      HandlerMethod hm = (HandlerMethod) handler; // 호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다
    }

    log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
    return true; // true로 해야 다음 인터셉터나 핸들러로 넘어감
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    log.info("postHandle [{}]", modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    String requestURI = request.getRequestURI();
    String logId = (String)request.getAttribute(LOG_ID);

    log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler);
    if (ex != null) {
      // error는 {} 없이 그냥 넣어주면 됨
      log.error("afterCompletion error!!", ex);
    }
  }

}
