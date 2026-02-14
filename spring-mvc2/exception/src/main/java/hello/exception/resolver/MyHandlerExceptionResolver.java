package hello.exception.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

  @Override
  public @Nullable ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex) {

    try {
      if (ex instanceof IllegalArgumentException) {
        log.info("IllegalArgumentException resolver to 400");
        // 예외를 이 코드에서 삼켜버리고 정상 객체를 반환함
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());

        // 이렇게 ModelAndView를 전달해도 비어있기 때문에 렌더링되지는 않음
        // 정상흐름으로 WAS까지 전달되면 sendError 사항을 보고 WAS는 에러 페이지를 뒤져보고 에러를 반환해줌
        return new ModelAndView();
      }
    } catch (IOException e) {
      log.error("resolver ex", ex);
    }
    // null 반환 시 예외가 계속 전달 됨
    return null;
  }
}
