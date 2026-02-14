package hello.exception.resolver;

import hello.exception.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import tools.jackson.databind.ObjectMapper;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public @Nullable ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex) {
    try {
      if (ex instanceof UserException) {
        log.info("UserException resolver to 400");
        String acceptHeader = request.getHeader("accept");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        if ("application/json".equals(acceptHeader)) {
          Map<String, Object> errorResult = new HashMap<>();
          errorResult.put("ex", ex.getClass());
          errorResult.put("message", ex.getMessage());

          response.setContentType("application/json");
          response.setCharacterEncoding("utf-8");
          // object -> Json -> String
          response.getWriter().write(objectMapper.writeValueAsString(errorResult));

          // 예외는 먹어버리지만 정상적으로 리턴되어 서블릿 컨테이너까지 response가 전달됨
          // 서블릿 컨테이너에 갔다가 다시 /error 같은 경로에 매핑된 컨트롤러를 찾지 않고
          // exception resolver에서 에러처리를 한 뒤 정상적인 값을 담아서 서블릿 컨테리너에 반환 -> 그냥 그게 반환됨
          return new ModelAndView();
        } else {
          // text/html
          return new ModelAndView("error/500");
        }
      }

    } catch (IOException e) {
      log.error("resolver ex", e);
    }

    return null;
  }
}
