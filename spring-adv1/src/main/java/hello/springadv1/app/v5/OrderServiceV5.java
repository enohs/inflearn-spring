package hello.springadv1.app.v5;

import hello.springadv1.trace.callback.TraceTemplate;
import hello.springadv1.trace.logtrace.LogTrace;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

  private final OrderRepositoryV5 orderRepository;
  private final TraceTemplate traceTemplate;

  public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace trace) {
    this.orderRepository = orderRepository;
    this.traceTemplate = new TraceTemplate(trace);
  }

  public void orderItem(String itemId) {
    traceTemplate.execute("OrderService5.orderItem()", () -> {
      orderRepository.save(itemId);
      return null;
    });
  }

}
