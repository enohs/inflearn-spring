package hello.springadv1;

import hello.springadv1.proxy.config.AppV1Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(AppV1Config.class)
@SpringBootApplication(scanBasePackages = {"hello.springadv1.advanced", "hello.springadv1.proxy.app.v3"})
public class SpringAdv1Application {

  public static void main(String[] args) {
    SpringApplication.run(SpringAdv1Application.class, args);
  }

}
