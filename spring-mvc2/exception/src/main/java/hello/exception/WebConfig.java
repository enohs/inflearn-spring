package hello.exception;

import hello.exception.filter.LogFilter;
import hello.exception.interceptor.LogInterceptor;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  // Interceptor는 DispatcherType으로 판단하지는 못함
  // 대신 excludePathPatterns를 활용해서 인터셉터 적용 여부를 결정할 수 있음
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LogInterceptor())
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns("/css/**", "*.ico", "/error", "/error-page/**"); // 오류 페이지 경로 입력
  }

  //@Bean
  public FilterRegistrationBean logFilter() {
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

    filterRegistrationBean.setFilter(new LogFilter());
    filterRegistrationBean.setOrder(1);
    filterRegistrationBean.addUrlPatterns("/*");
    filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR); // 두 타입 중 하나에 해당될 때 등록된 필터가 동작함을 의미 (기본값은 REQUEST)

    return filterRegistrationBean;
  }

}
