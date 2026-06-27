package hello.springadv1.trace.callback;

public interface TraceCallback<T> {

  T call();

}
