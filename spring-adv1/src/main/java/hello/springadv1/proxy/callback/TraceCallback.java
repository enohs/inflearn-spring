package hello.springadv1.proxy.callback;

public interface TraceCallback<T> {

  T call();

}
