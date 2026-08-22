package org.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("HelloServlet.service");
    resp.getWriter().println("hello servlet!"); // 응답으로 보내는 메시지
  }
}
