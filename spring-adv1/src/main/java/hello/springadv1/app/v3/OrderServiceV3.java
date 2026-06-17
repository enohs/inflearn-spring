package hello.springadv1.app.v3;

import hello.springadv1.trace.TraceStatus;
import hello.springadv1.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

  private final OrderRepositoryV3 orderRepository;
  private final LogTrace trace;

  public void orderItem(String itemId) {
    TraceStatus status = null;
    try {
      status = trace.begin("OrderService3.orderItem()");
      orderRepository.save(itemId);
      trace.end(status);
    } catch (Exception e) {
      trace.exception(status, e);
      throw e; // 예외를 꼭 다시 던져줘야 한다.
    }
  }

}
