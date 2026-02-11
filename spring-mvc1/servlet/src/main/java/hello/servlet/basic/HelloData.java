package hello.servlet.basic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 잭슨 라이브러리가 자바빈 프로퍼티 접근법을 사용하기에 필요
public class HelloData {

  private String username;
  private int age;

}
