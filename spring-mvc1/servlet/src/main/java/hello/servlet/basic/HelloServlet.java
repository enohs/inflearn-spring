package hello.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// urlPatterns에 따라 자동 호출
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

  @Override // 본 서블릿이 호출되면 해당 서비스 메서드가 호출된다
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println("HelloServlet.service");

    // request와 response는 WAS가 요청을 받아 생성해서 보내줌
    System.out.println("request = " + request);
    System.out.println("response = " + response);
    // HttpServletRequest나 HttpServletResponse는 인터페이스 -> WAS 서버들이 구현체를 만들고, 아래처럼 확인됨
    // request = org.apache.catalina.connector.RequestFacade@70f6809f
    //response = org.apache.catalina.connector.ResponseFacade@7f148a74

    String username = request.getParameter("username");
    System.out.println("username = " + username);

    // header
    response.setContentType("text/plain");
    response.setCharacterEncoding("utf-8");
    // message body
    response.getWriter().write("hello " + username);
  }
}
