package hello.springmvc.basic.requestMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MappingController {

  private final Logger log = LoggerFactory.getLogger(getClass());

  // 배열로 여러 개 입력 가능
  @RequestMapping(value = {"/hello-basic", "/hello-go"})
  public String helloBasic() {
    log.info("helloBasic");
    return "ok";
  }

  @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
  public String mappingGetV1() {
    log.info("helloBasic");
    return "ok";
  }

  @GetMapping("/mapping-get-v2")
  public String mappingGetV2() {
    log.info("helloBasic");
    return "ok";
  }

  /**
   * @PathVariable 사용
   * @식별자와 변수명이 같으면 생략 가능
   * @PathVariable("userId") String userId -> @PathVariable String userId
   */
  @GetMapping("/mapping/{userId}")
  public String mappingPath(@PathVariable("userId") String data) {
    log.info("mappingPath userId={}", data);
    return "ok";
  }

  /**
   * PathVariable 다중 사용
   */
  @GetMapping("/mapping/users/{userId}/orders/{orderId}")
  public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
    log.info("mappingPath userId={}, orderId={}", userId, orderId);
    return "ok";
  }

  /**
   * 파라미터로 추가 매핑 (해당 파라미터가 있어야만 호출되도록 하는 용도. 잘 안 씀)
   * params="mode",
   * params="!mode",
   * params="mode=debug",
   * params="mode!=debug",
   * params={"mode=debug","data=good"}
   */
  @GetMapping(value = "/mapping-param", params = "mode=debug")
  public String mappingParam() {
    log.info("mappingParam");
    return "ok";
  }

  // 파라미터와 마찬가지로 해당 헤더가 있어야 호출되도록 함 (적용 방식은 파라미터 참고)
  @GetMapping(value = "/mapping-header", headers = "mode=debug")
  public String mappingHeader() {
    log.info("mappingHeader");
    return "ok";
  }

  /**
   * Content-Type 헤더 기반 추가 매핑 Media Type
   * consumes="application/json"
   * consumes="!application/json"
   * consumes="application/*"
   * consumes="*\/*"
   * MediaType.APPLICATION_JSON_VALUE
   */
  @PostMapping(value = "/mapping-consume", consumes = "application/json")
  public String mappingConsumes() {
    log.info("mappingConsumes");
    return "ok";
  }

  /**
   * Accept 헤더 기반 Media Type (Accept 내용에 따름)
   * produces = "text/html"
   * produces = "!text/html"
   * produces = "text/*"
   * produces = "*\/*"
   */
  @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
  public String mappingProduces() {
    log.info("mappingProduces");
    return "ok";
  }

}
