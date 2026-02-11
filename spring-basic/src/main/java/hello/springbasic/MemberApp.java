package hello.springbasic;

import hello.springbasic.member.Grade;
import hello.springbasic.member.Member;
import hello.springbasic.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

  public static void main(String[] args) {
    // 이제 객체 생성 및 생성자 호출은 AppConfig가 담당하게 된다
    //AppConfig appConfig = new AppConfig();
    //MemberService memberService = appConfig.memberService(); // appConfig 사용하는 방법
    // MemberService memberService = new MemberServiceImpl(); // 제일 초보적인 방법

    // 사실상 빈을 관리하는 스프링 컨테이너
    // AppConfig에 있는 설정 정보를 가지고 스프링이 @빈을 컨테이너에 전부 담아 관리해줌
    // .class는 스프링 컨테이너 활용할 구성 정보 전달 대상이다
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    // 메서드 이름과 타입을 전달해준다
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

    Member member = new Member(1L, "memberA", Grade.VIP);
    memberService.join(member);

    Member findMember = memberService.findMember(1L);
    System.out.println("new member = " + member.getName());
    System.out.println("findMember = " + findMember.getName());
  }
}
