package hello.springadv1.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

  // 스프링 컨테이너에서 객체 조회하는 것을 스프링 빈 생성 시점이 아니라 실제 객체를 사용하는 시점으로 지연
  // 이대로도 잘 돌아가지만 ApplicationContext는 기능을 과하게 지원함
  // private final ApplicationContext applicationContext;
  private final ObjectProvider<CallServiceV2> callServiceProvider;

  public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider) {
    this.callServiceProvider = callServiceProvider;
  }

  public void external() {
    log.info("call external");
    //CallServiceV2 callService = applicationContext.getBean(CallServiceV2.class);
    CallServiceV2 callService = callServiceProvider.getObject();
    callService.internal(); // 내부 메서드 호출
  }

  public void internal() {
    log.info("call internal");
  }

}
