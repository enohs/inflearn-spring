package org.container;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;
import java.util.Set;

// onStartup 메서드의 파라미터 c에 @HandlesTypes에서 등록한 인터페이스의 구현체가 담긴다
@HandlesTypes(AppInit.class)
public class MyContainerInitV2 implements ServletContainerInitializer {

  @Override
  public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
    System.out.println("MyContainerInitV2.onStartup");
    System.out.println("MyContainerInitV2 c = " + c);
    System.out.println("MyContainerInitV2 ctx = " + ctx);

    // [class org.container.AppInitV1Servlet] c로 여러 클래스가 들어올 수 있음
    for (Class<?> appInitClass : c) {
      try {
        // 객체가 아닌 클래스 정보가 전달되기 때문에 객체 생성해서 사용해야 함
        // new AppInitV1Servlet()과 같은 코드
        AppInit appInit = (AppInit) appInitClass.getDeclaredConstructor().newInstance();
        // 서블릿 컨텍스트 전달 및 서블릿 등록
        appInit.onStartup(ctx);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
