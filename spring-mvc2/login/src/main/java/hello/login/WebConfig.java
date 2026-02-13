package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

  // 따로 필터를 등록해줘야 실제로 필터 사이에서 돌아가게 됨
  @Bean
  public FilterRegistrationBean logFilter() {
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new LogFilter()); // 필터 삽입
    filterRegistrationBean.setOrder(1); // 순서
    filterRegistrationBean.addUrlPatterns("/*"); // 어떤 URL 패턴에 대해 필터를 동작시킬 것인지 설정

    return filterRegistrationBean;
  }

  @Bean
  public FilterRegistrationBean loginCheckFilter() {
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new LoginCheckFilter());
    filterRegistrationBean.setOrder(2); // 순서
    filterRegistrationBean.addUrlPatterns("/*"); // 어떤 URL 패턴에 대해 필터를 동작시킬 것인지 설정

    return filterRegistrationBean;
  }

}
