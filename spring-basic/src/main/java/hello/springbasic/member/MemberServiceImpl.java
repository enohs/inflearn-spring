package hello.springbasic.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// 인터페이스 구현체가 하나만 있으면 관례상 뒤에 Impl을 붙임
@Component // 빈에 자동으로 등록될 때 메서드명이 아닌 클래스명으로 등록 (앞글자만 소문자) + 이름 변경하고 싶으면 @Component("memberService2")처럼 바꿀 수 있음
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  // 추상화와 구현체 모두 의존 -> DIP 위반 중
  // 이후 생성자로 의존성 주입을 받으면서 이 클래스는 추상에만 의존하게 됨!!
  private final MemberRepository memberRepository;

  // @Component를 사용해서 자동으로 스캔 후 스프링 빈으로 등록하면 의존성 주입을 할 방법이 필요 -> @Autowired 사용 (타입에 맞는 객체를 가져와서 의존성 자동 주입)
  // @RequiredArgsConstructor를 사용해서 생성자 지움

  @Override
  public void join(Member member) {
    memberRepository.save(member);
  }

  @Override
  public Member findMember(Long memberId) {
    return memberRepository.findById(memberId);
  }

  // 테스트 용도
  public MemberRepository getMemberRepository() {
    return memberRepository;
  }
}
