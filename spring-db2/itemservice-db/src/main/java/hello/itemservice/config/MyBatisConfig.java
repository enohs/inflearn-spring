package hello.itemservice.config;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.mybatis.ItemMapper;
import hello.itemservice.repository.mybatis.MyBastisItemRepository;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {

  private final ItemMapper itemMapper;

  @Bean
  public ItemService itemService() {
    return new ItemServiceV1(itemRepository());
  }

  @Bean
  public ItemRepository itemRepository() {
    // Mybatis 모듈이 트랜잭션 매니저나 데이터 소스를 다 읽어서 매퍼와 연결해줌
    return new MyBastisItemRepository(itemMapper);
  }
}
