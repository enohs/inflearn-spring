package hello.springadv1.proxy.config.v1_proxy.interface_proxy;

import hello.springadv1.proxy.app.v1.OrderServiceV1;
import hello.springadv1.proxy.trace.TraceStatus;
import hello.springadv1.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderServiceInterfaceProxy implements OrderServiceV1 {

  private final OrderServiceV1 target;
  private final LogTrace logTrace;

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
