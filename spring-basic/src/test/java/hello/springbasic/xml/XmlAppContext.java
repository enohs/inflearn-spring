package hello.springbasic.xml;

import static org.assertj.core.api.Assertions.assertThat;

import hello.springbasic.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class XmlAppContext {

  @Test
  void xmlAppContext() {
    // 클래스 패스에 있는 걸 읽어서 resources 안에 있는 xml 가져다가 읽음
    // 사실상 xml로 AppConfig를 작성한 것
    ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    assertThat(memberService).isInstanceOf(MemberService.class);
  }

}
