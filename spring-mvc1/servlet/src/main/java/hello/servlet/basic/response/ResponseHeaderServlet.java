package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // [status-line]
    response.setStatus(HttpServletResponse.SC_OK);

    // [response-headers]
    //response.setHeader("Content-Type", "text/plain;charset=utf-8"); // 인코딩 정보 입력 안 해주면 한글 깨짐
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 캐시 무효화
    response.setHeader("Pragma", "no-cache"); // 캐시 무효화
    response.setHeader("my-header", "helo"); // 임의의 헤더 추가

    // [Header 편의 메서드들]
    content(response);
    cookie(response);
    redirect(response);

    // [message body]
    response.getWriter().println("안녕하세요");
  }

  private void content(HttpServletResponse response) {
    //Content-Type: text/plain;charset=utf-8
    //Content-Length: 2
    //response.setHeader("Content-Type", "text/plain;charset=utf-8");
    // 아래 방식으로 변경 가능
    response.setContentType("text/plain");
    response.setCharacterEncoding("utf-8");
    //response.setContentLength(2); //(생략시 자동 생성)
  }

  private void cookie(HttpServletResponse response) {
    //Set-Cookie: myCookie=good; Max-Age=600;
    //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
    Cookie cookie = new Cookie("myCookie", "good");
    cookie.setMaxAge(600); //600초
    response.addCookie(cookie);
  }

  private void redirect(HttpServletResponse response) throws IOException {
    //Status Code 302
    //Location: /basic/hello-form.html

    //response.setStatus(HttpServletResponse.SC_FOUND); //302
    //response.setHeader("Location", "/basic/hello-form.html");
    // 아래 코드로 대체 가능
    response.sendRedirect("/basic/hello-form.html");
  }
}
