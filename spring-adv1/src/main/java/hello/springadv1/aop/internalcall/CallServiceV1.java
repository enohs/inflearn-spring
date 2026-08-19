package hello.springadv1.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

  // 자기 자신을 주입 받지만 빈 생성 후 프록시 생성된 후기 때문에 프록시가 주입됨
  private CallServiceV1 callService;

  // 자기 자신이 생기지도 않았는데 의존성 주입하는 건 말이 안 됨
//  @Autowired
//  public CallServiceV1(CallServiceV1 callService) {
//    this.callService = callService;
//  }

  // setter는 객체 생성 후 주입 가능
  @Autowired
  public void setCallServiceV1(CallServiceV1 callService) {
    log.info("callServiceV1 setter={}", callService.getClass());
    this.callService = callService;
  }

  public void external() {
    log.info("call external");
    callService.internal(); // 내부 메서드 호출
  }

  public void internal() {
    log.info("call internal");
  }

}
