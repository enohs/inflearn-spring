package hello.itemservice.config;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.jpa.JpaItemRepositoryV3;
import hello.itemservice.repository.v2.ItemQueryRepositoryV2;
import hello.itemservice.repository.v2.ItemRepositoryV2;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV2;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class V2Config {

  private final EntityManager em;
  private final ItemRepositoryV2 itemRepositoryV2; // Spring Data Jpa에서 제공 (JPA Repository 상속 받으면 빈등록 자동이기 때문에 주입 받기만 하면 됨)

  @Bean
  public ItemService itemService() {
    return new ItemServiceV2(itemRepositoryV2, itemQueryRepositoryV2());
  }

  @Bean
  public ItemQueryRepositoryV2 itemQueryRepositoryV2() {
    return new ItemQueryRepositoryV2(em);
  }

  // 이거는 테스트 데이터 초기화 기능 때문에 필요한 설정
  @Bean
  public ItemRepository itemRepository() {
    return new JpaItemRepositoryV3(em);
  }

}
