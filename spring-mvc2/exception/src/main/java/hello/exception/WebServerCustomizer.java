package hello.exception;

import org.springframework.boot.web.error.ErrorPage;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

  // WAS 오류 페이지 커스텀
  @Override
  public void customize(ConfigurableWebServerFactory factory) {
    ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
    ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");

    // RuntimeException의 자식 에러들도 페이지 이동해줌
    ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");

    factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
  }
}
