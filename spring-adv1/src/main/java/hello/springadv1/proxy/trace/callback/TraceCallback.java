package hello.springadv1.proxy.trace.callback;

public interface TraceCallback<T> {

  T call();

}
