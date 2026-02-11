package hello.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class RequestBodyStringController {

  @RequestMapping("/request-body-string-v1")
  public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // stream은 바이트 코드
    ServletInputStream inputStream = request.getInputStream();
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

    log.info("messageBody={}", messageBody);
    response.getWriter().write("ok");
  }

  @RequestMapping("/request-body-string-v2")
  public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
    log.info("messageBody={}", messageBody);
    responseWriter.write("ok");
  }

  @RequestMapping("/request-body-string-v3")
  // HttpEntity는 메시지 바디 정보를 조회하는 것이지, 요청 파라미터를 조회하는 기능과는 관계 없음
  // 요청 파라미터는 @RequestParam이나 @ModelAttribute 사용
  public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
    String messageBody = httpEntity.getBody();
    log.info("messageBody={}", messageBody);

    // 헤더 정보 포함 가능. 뷰 조회 x. 메시지 바디 정보 직접 반환
    return new HttpEntity<>("ok");
  }

  @RequestMapping("/request-body-string-v3-1")
  public HttpEntity<String> requestBodyStringV31(RequestEntity<String> httpEntity) throws IOException {
    String messageBody = httpEntity.getBody();
    log.info("messageBody={}", messageBody);

    // 헤더 정보 포함 가능. 뷰 조회 x. 메시지 바디 정보 직접 반환
    return new ResponseEntity<>("ok", HttpStatus.OK);
  }

  @ResponseBody
  @RequestMapping("/request-body-string-v4")
  // 엔터티 사용하지 않고 바로 바디값 읽어서 전달해줌
  // 헤더 값이 필요하면 HttpEntity를 사용하거나 @RequestHeader 사용
  // @RequestBody도 요청 파라미터 조회와는 관계 없음
  public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
    log.info("messageBody={}", messageBody);

    return "ok";
  }

}
