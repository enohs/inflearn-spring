package hello.springadv1.trace.hellotrace;

import hello.springadv1.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class HelloTraceV2Test {

  @Test
  void Begin_end() {
    HelloTraceV2 trace = new HelloTraceV2();
    TraceStatus status1 = trace.begin("hello1");
    TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
    trace.end(status2);
    trace.end(status1);
  }

  @Test
  void Begin_exception() {
    HelloTraceV2 trace = new HelloTraceV2();
    TraceStatus status1 = trace.begin("hello1");
    TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
    trace.exception(status2, new IllegalArgumentException());
    trace.exception(status1, new IllegalArgumentException());
  }

}