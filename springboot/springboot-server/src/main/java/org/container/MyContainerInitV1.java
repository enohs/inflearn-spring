package org.container;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import java.util.Set;

// WAS 서블릿 컨테이너 초기화하는 인터페이스 구현
public class MyContainerInitV1 implements ServletContainerInitializer {

  // 톰캣이 실행하는 메서드
  @Override
  public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
    System.out.println("MyContainerInitV1.onStartup");
    System.out.println("MyContainerInitV1 c = " + c);
    System.out.println("MyContainerInitV1 ctx = " + ctx);
  }
}
