package hello.springadv1.proxy.config.v1_proxy.concrete_proxy;

import hello.springadv1.proxy.app.v2.OrderServiceV2;
import hello.springadv1.proxy.trace.TraceStatus;
import hello.springadv1.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {

  private final OrderServiceV2 target;
  private final LogTrace logTrace;

  public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
    super(null); // 문법상 생성자에 의해 강제로 호출해야 하는데 부모의 기능은 사용하지 않을 것이기에 null 사용 가능
    this.target = target;
    this.logTrace = logTrace;
  }

  @Override
  public void orderItem(String itemId) {
    TraceStatus status = null;
    try {
      status = logTrace.begin("OrderService.orderItem()");

      target.orderItem(itemId);

      logTrace.end(status);
    } catch (Exception e) {
      logTrace.exception(status, e);
      throw e;
    }
  }
}
