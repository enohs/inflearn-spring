package hello.springadv1.aop.internalcall;

import hello.springadv1.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV0Test {

  @Autowired
  CallServiceV0 callService;

  @Test
  public void external() {
    callService.external();
  }

  @Test
  public void internal() {
    callService.internal();
  }

}