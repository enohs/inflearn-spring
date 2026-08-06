package hello.springadv1;

import hello.springadv1.proxy.config.v4_postprocessor.BeanPostProcessorConfig;
import hello.springadv1.proxy.config.v5_autoproxy.AutoProxyConfig;
import hello.springadv1.proxy.config.v6_aop.AopConfig;
import hello.springadv1.proxy.trace.logtrace.LogTrace;
import hello.springadv1.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@Import({AppV1Config.class, AppV2Config.class})
//@Import({InterfaceProxyConfig.class, ConcreteProxyConfig.class})
//@Import(DynamicProxyBasicConfig.class)
//@Import(DynamicProxyFilterConfig.class)
//@Import(ProxyFactoryConfigV1.class)
//@Import(ProxyFactoryConfigV2.class)
//@Import(BeanPostProcessorConfig.class)
//@Import(AutoProxyConfig.class)
@Import(AopConfig.class)
@SpringBootApplication(scanBasePackages = {"hello.springadv1.advanced", "hello.springadv1.proxy.app.v3", "hello.springadv1.aop"})
public class SpringAdv1Application {

  public static void main(String[] args) {
    SpringApplication.run(SpringAdv1Application.class, args);
  }

  @Bean
  public LogTrace logTraceProxy() {
    return new ThreadLocalLogTrace();
  }

}
