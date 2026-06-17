package hello.springadv1.trace.logtrace;

import hello.springadv1.trace.TraceStatus;

public interface LogTrace {

  TraceStatus begin(String message);

  void end(TraceStatus status);

  void exception(TraceStatus status, Exception e);

}
