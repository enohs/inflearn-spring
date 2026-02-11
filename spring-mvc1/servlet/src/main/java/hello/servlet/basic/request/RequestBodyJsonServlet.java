package hello.servlet.basic.request;

import hello.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.util.StreamUtils;
import tools.jackson.databind.ObjectMapper;

@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ServletInputStream inputStream = request.getInputStream();
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
    // json도 문자이기에 그대로 출력 가능
    System.out.println("messageBody = " + messageBody);

    // jackson이라는 json라이브러리 사용해서 json을 객체로 변환
    HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

    System.out.println("helloData.getUsername() = " + helloData.getUsername());
    System.out.println("helloData.getAge() = " + helloData.getAge());

    response.getWriter().write("ok");
  }
}
