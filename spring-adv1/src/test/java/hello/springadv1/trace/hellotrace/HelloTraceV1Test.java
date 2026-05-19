package hello.springadv1.trace.hellotrace;

import hello.springadv1.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class HelloTraceV1Test {

  @Test
  void Begin_end() {
    HelloTraceV1 trace = new HelloTraceV1();
    TraceStatus status = trace.begin("hello");
    trace.end(status);
  }

  @Test
  void Begin_exception() {
    HelloTraceV1 trace = new HelloTraceV1();
    TraceStatus status = trace.begin("hello");
    trace.exception(status, new IllegalArgumentException());
  }

}