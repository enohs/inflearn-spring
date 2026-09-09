package hello.springbootautoconfig.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.TransactionManager;

@Slf4j
@Configuration // 해당 어노테이션을 주석처리해도 아래의 클래스들은 스프링이 자동구성해준다
public class DbConfig {

  @Bean
  public DataSource dataSource() {
    log.info("dataSource 빈 등록");
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setDriverClassName("org.h2.Driver");
    dataSource.setJdbcUrl("jdbc:h2:mem:test");
    dataSource.setUsername("sa");
    dataSource.setPassword("");
    return dataSource;
  }

  @Bean
  public TransactionManager transactionManager() {
    log.info("transactionManager 빈 등록");
    return new JdbcTransactionManager(dataSource());
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    log.info("jdbcTemplate 빈 등록");
    return new JdbcTemplate(dataSource());
  }

}
