package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RequestHeaderController {

  @RequestMapping("/headers")
  public String headers(
      HttpServletRequest request,
      HttpServletResponse response,
      HttpMethod httpMethod,
      Locale locale, // 언어 정보
      @RequestHeader MultiValueMap<String, String> headerMap, // 헤더 한 번에 여러 개 받음 (MultiValueMap은 하나의 키에 여러 value가 리스트로 담기는 구조)
      @RequestHeader("host") String host, // 헤더 하나만 받음
      @CookieValue(value = "myCookie", required = false) String cookie // 쿠키도 받을 수 있음. required는 쿠키가 꼭 있어야 하는지 여부
  ) {
    log.info("request={}", request);
    log.info("response={}", response);
    log.info("httpMethod={}", httpMethod);
    log.info("locale={}", locale);
    log.info("headerMap={}", headerMap);
    log.info("header host={}", host);
    log.info("myCookie={}", cookie);

    return "ok";
  }
}
