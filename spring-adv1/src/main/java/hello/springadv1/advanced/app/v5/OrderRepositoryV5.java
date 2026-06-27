package hello.springadv1.advanced.app.v5;

import hello.springadv1.advanced.trace.callback.TraceTemplate;
import hello.springadv1.advanced.trace.logtrace.LogTrace;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV5 {

  private final TraceTemplate traceTemplate;

  public OrderRepositoryV5(LogTrace trace) {
    traceTemplate = new TraceTemplate(trace);
  }

  public void save(String itemId) {
    traceTemplate.execute("OrderRepositoryV5.save()", () -> {
      if (itemId.equals("ex")) {
        throw new IllegalArgumentException("예외 발생");
      }
      sleep(1000);
      return null;
    });
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
