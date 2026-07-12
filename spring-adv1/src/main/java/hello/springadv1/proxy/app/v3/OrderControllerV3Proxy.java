package hello.springadv1.proxy.app.v3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerV3Proxy {

  private final OrderServiceV3Proxy orderService;

  public OrderControllerV3Proxy(OrderServiceV3Proxy orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/v3/proxy/request")
  public String request(@RequestParam("itemId") String itemId) {
    orderService.orderItem(itemId);
    return "ok";
  }

  @GetMapping("/v3/proxy/no-log")
  public String noLog() {
    return "ok";
  }

}