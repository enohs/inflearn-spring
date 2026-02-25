package hello.itemservice;

import hello.itemservice.config.JpaConfig;
import hello.itemservice.config.SpringDataJpaConfig;
import hello.itemservice.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Slf4j
//@Import(MemoryConfig.class)
//@Import(JdbcTemplateV1Config.class) // 수동 빈등록 클래스로 설정
//@Import(JdbcTemplateV2Config.class)
//@Import(JdbcTemplateV3Config.class)
//@Import(MyBatisConfig.class)
//@Import(JpaConfig.class)
@Import(SpringDataJpaConfig.class)
@SpringBootApplication(scanBasePackages = "hello.itemservice.web") // 서비스와 레포지토리는 수동등록을 위해 컨트롤러 패키지로 스캔 제한
public class ItemServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ItemServiceApplication.class, args);
  }

  @Bean // EventListener 사용을 위한 빈 등록
  @Profile("local") // 특정 프로필이 활성화되어 있을 때만 빈으로 등록하는 기능. 해당 설정은 properties에서 함
  public TestDataInit testDataInit(ItemRepository itemRepository) {
    return new TestDataInit(itemRepository);
  }

  // 임베디드 모드 DB 사용을 위한 수동 빈 등록
  /*@Bean
  @Profile("test")
  public DataSource dataSource() {
    log.info("메모리 데이터베이스 초기화");
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.h2.Driver");
    dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
    dataSource.setUsername("sa");
    dataSource.setPassword("");
    return dataSource;
  }*/
}
