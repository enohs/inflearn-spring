package org.container;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration.Dynamic;
import org.spring.HelloConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

// 서블릿 컨테이너에 직접 등록하지 않아도 되도록 만들어 놓은 인터페이스
public class AppInitV3SpringMvc implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    System.out.println("AppInitV3SpringMvc.onStartup");

    // 스프링 컨테이너 생성
    AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
    appContext.register(HelloConfig.class);

    // 스프링 MVC 디스패처 서블릿 생성, 스프링 컨테이너 연결
    DispatcherServlet dispatcher = new DispatcherServlet(appContext);

    // 디스패처 서블릿을 서블릿 컨테이너에 등록
    Dynamic servlet = servletContext.addServlet("dispatcherV3", dispatcher);

    // 모든 요청이 디스패처 서블릿을 통하도록 설정
    servlet.addMapping("/");
  }
}
