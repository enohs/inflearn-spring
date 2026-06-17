package hello.springadv1.trace.logtrace;

import hello.springadv1.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class FieldLogTraceTest {

  FieldLogTrace trace = new FieldLogTrace();

  @Test
  void vegin_end_level2() {
    TraceStatus status1 = trace.begin("hello1");
    TraceStatus status2 = trace.begin("hello2");
    trace.end(status2);
    trace.end(status1);
  }

  @Test
  void vegin_exception_level2() {
    TraceStatus status1 = trace.begin("hello1");
    TraceStatus status2 = trace.begin("hello2");
    trace.exception(status2, new IllegalStateException());
    trace.exception(status1, new IllegalStateException());
  }

}