package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class RequestParamController {

  @RequestMapping("/request-param-v1")
  public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));
    log.info("username={}, age={}", username, age);

    response.getWriter().write("ok");
  }

  @ResponseBody // 뷰를 찾지 않고 반환 문자를 http 메시지 바디에 그대로 넣어 보냄 (@RestController와 같은 기능)
  @RequestMapping("/request-param-v2")
  public String requestParamV2(
      @RequestParam("username") String memberName,
      @RequestParam("age") int memberAge
  ) {
    log.info("username={}, age={}", memberName, memberAge);
    return "ok";
  }

  @ResponseBody
  @RequestMapping("/request-param-v3")
  public String requestParamV3(
      // 파라미터 키값과 변수명이 같으면 생략 가능
      @RequestParam String username,
      @RequestParam int age
  ) {
    log.info("username={}, age={}", username, age);
    return "ok";
  }

  @ResponseBody
  @RequestMapping("/request-param-v4")
  public String requestParamV4(
      // 파라미터 키값과 변수명이 같고 String, int, Integer 같은 단순한 타입이면 @RequestParam도 생략 가능
      String username,
      int age
  ) {
    log.info("username={}, age={}", username, age);
    return "ok";
  }

  @ResponseBody
  @RequestMapping("/request-param-required")
  public String requestParamRequired(
      @RequestParam(required = true) String username, // required여도 username= 으로 입력하면 빈 문자열이 들어올 수 있다는 점 알아두기
      @RequestParam(required = false) Integer age
  ) {
    log.info("username={}, age={}", username, age);
    return "ok";
  }

  @ResponseBody
  @RequestMapping("/request-param-default")
  public String requestParamDefault(
      // 기본값 설정 시 빈 문자열이 들어와도 기본값으로 바꿔줌
      @RequestParam(required = true, defaultValue = "guest") String username,
      @RequestParam(required = false, defaultValue = "-1") int age
  ) {
    log.info("username={}, age={}", username, age);
    return "ok";
  }

  @ResponseBody
  @RequestMapping("/request-param-map")
  // 한 번에 모든 파라미터 담을 수 있음 (MultiValueMap 사용 가능)
  public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
    log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
    return "ok";
  }

  @ResponseBody
  @RequestMapping("/model-attribute-v0")
  public String modelAttributeV0(@RequestParam String username, @RequestParam int age) {
    HelloData helloData = new HelloData();
    helloData.setUsername(username);
    helloData.setAge(age);

    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
    log.info("helloData={}", helloData); // @Data 안에 @ToString이 보기 좋게 출력해줌

    return "ok";
  }

  @ResponseBody
  @RequestMapping("/model-attribute-v1")
  // 객체를 생성하고 파라미터에 맞는 프로퍼티를 찾아 setter로 값을 입력(바인딩)해줌
  public String modelAttributeV1(@ModelAttribute HelloData helloData) {
    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
    log.info("helloData={}", helloData);

    return "ok";
  }

  @ResponseBody
  @RequestMapping("/model-attribute-v2")
  // @ModelAttribute 생략 가능. 근데 @RequestParam도 생략 가능한데...?
  // 스프링은 String, int, Integer 같은 단순한 타입이면 @RequestParam,
  // 나머지는 @ModelAttribute로 인식한다 (argument resolver(일종의 예약어)로 지정해둔 타입 외)
  public String modelAttributeV2(HelloData helloData) {
    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
    log.info("helloData={}", helloData);

    return "ok";
  }

}
