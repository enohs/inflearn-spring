package hello.typeconverter;

import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import hello.typeconverter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    // 주석처리 우선순위 때문 (컨버터가 포맷터보다 우선순위를 가지는데,
    // MyNumberFormatter와 StringToIntegerConverter의 기능이 문자 -> 숫자, 숫자 -> 문자와 같기 때문에 주석처리)
    //registry.addConverter(new StringToIntegerConverter());
    //registry.addConverter(new IntegerToStringConverter());
    registry.addConverter(new IpPortToStringConverter());
    registry.addConverter(new StringToIpPortConverter());

    // 추가
    registry.addFormatter(new MyNumberFormatter());
  }
}
