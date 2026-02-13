package hello.login;

import hello.login.web.argumentResolver.LoginMemberArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import jakarta.servlet.Filter;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  // 직접 만든 argument resolver 등록 (어노테이션 직접 만들어서 활용)
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new LoginMemberArgumentResolver());
  }

  // 스프링 인터셉터 등록 방식 (WebMvcConfigurer 구현 후 등록)
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LogInterceptor())
        .order(1)
        .addPathPatterns("/**") // 서블릿과는 패턴이 약간 다름
        .excludePathPatterns("/css/**", "/*.ico", "/error"); // 인터셉트 안 걸리게 하는 경로 설정 (필터도 가능은 함)

    // 인터셉터 추가
    registry.addInterceptor(new LoginCheckInterceptor())
        .order(2)
        .addPathPatterns("/**")
        .excludePathPatterns("/login", "/members/add", "/", "/logout", "/css/**", "/*.ico", "/error");
  }

  // 서블릿 필터 등록 방식
  // 따로 필터를 등록해줘야 실제로 필터 사이에서 돌아가게 됨
  //@Bean
  public FilterRegistrationBean logFilter() {
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new LogFilter()); // 필터 삽입
    filterRegistrationBean.setOrder(1); // 순서
    filterRegistrationBean.addUrlPatterns("/*"); // 어떤 URL 패턴에 대해 필터를 동작시킬 것인지 설정

    return filterRegistrationBean;
  }

  //@Bean
  public FilterRegistrationBean loginCheckFilter() {
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new LoginCheckFilter());
    filterRegistrationBean.setOrder(2); // 순서
    filterRegistrationBean.addUrlPatterns("/*"); // 어떤 URL 패턴에 대해 필터를 동작시킬 것인지 설정

    return filterRegistrationBean;
  }

}
