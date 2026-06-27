package hello.springadv1.proxy.trace.logtrace;

import hello.springadv1.proxy.trace.TraceStatus;

public interface LogTrace {

  TraceStatus begin(String message);

  void end(TraceStatus status);

  void exception(TraceStatus status, Exception e);

}
