package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Controller
public class RequestBodyJsonController {

  private ObjectMapper objectMapper = new ObjectMapper();

  @PostMapping("/request-body-json-v1")
  public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ServletInputStream inputStream = request.getInputStream();
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
    log.info("messageBody={}", messageBody);

    HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

    response.getWriter().write("ok");
  }

  @ResponseBody
  @PostMapping("/request-body-json-v2")
  public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
    log.info("messageBody={}", messageBody);

    HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

    return "ok";
  }

  @ResponseBody
  @PostMapping("/request-body-json-v3")
  // HttpEntity나 @RequestBody를 사용하면 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 원하는 문자나 객체 등으로 바꿔준다
  // 컨버터는 문자뿐만 아니라 JSON도 객체로 변환해준다
  // 이때 @RequestBody가 없으면 @ModelAttribute로 생각하기 때문에 생략 불가
  public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
    return "ok";
  }

  @ResponseBody
  @PostMapping("/request-body-json-v4")
  // HttpEntity는 어노테이션 없이 사용 가능
  public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) throws IOException {
    HelloData body = httpEntity.getBody();
    log.info("username={}, age={}", body.getUsername(), body.getAge());
    return "ok";
  }

  @ResponseBody // 객체 -> HTTP 메시지 컨버터 -> JSON 응답 (Accept로 판단)
  @PostMapping("/request-body-json-v5")
  // JSON 요청 -> HTTP 메시지 컨버터 -> 객체 (Content-Type으로 판단)
  public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) throws IOException {
    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
    return helloData;
  }
}
