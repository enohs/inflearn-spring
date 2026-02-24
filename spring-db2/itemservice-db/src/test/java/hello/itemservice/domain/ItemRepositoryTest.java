package hello.itemservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import hello.itemservice.repository.memory.MemoryItemRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

// 원래 서비스에서 쓰던 대로라면 로직이 성공하면 커밋해야하는데 지금 롤백을 하고 있다!
// 테스트에서 사용하면 예외적으로 트랜잭션을 자동으로 롤백해주기 때문 (트랜잭션 생성과 롤백을 자동으로 처리해줌)
// 사실상 DB 정보를 따로 설정하지 않으면 스프링이 임베디드 DB를 사용하기 때문에 @Transactional 하나만 붙이면 DB테스트가 가능해진다 (대신 resources에 schema.sql 파일 안에 초기 생성을 위한 테이블 DDL이 있어야 함)
@Transactional
@SpringBootTest
class ItemRepositoryTest {

  @Autowired
  ItemRepository itemRepository;

/*  @Autowired
  PlatformTransactionManager transactionManager;
  TransactionStatus status;

  @BeforeEach
  void beforeEach() {
    // 트랜잭션 시작
    status = transactionManager.getTransaction(new DefaultTransactionDefinition());
  }
 */

  @AfterEach
  void afterEach() {
    //MemoryItemRepository 의 경우 제한적으로 사용
    if (itemRepository instanceof MemoryItemRepository) {
      ((MemoryItemRepository) itemRepository).clearStore();
    }

    // 트랜잭션 롤백
    //transactionManager.rollback(status);
  }

  //@Commit // 중간에 롤백이 아니라 커밋 하고 싶으면 @Transactional + @Commit을 사용하면 된다
  @Test
  void save() {
    //given
    Item item = new Item("itemA", 10000, 10);

    //when
    Item savedItem = itemRepository.save(item);

    //then
    Item findItem = itemRepository.findById(item.getId()).get();
    assertThat(findItem).isEqualTo(savedItem);
  }

  @Test
  void updateItem() {
    //given
    Item item = new Item("item1", 10000, 10);
    Item savedItem = itemRepository.save(item);
    Long itemId = savedItem.getId();

    //when
    ItemUpdateDto updateParam = new ItemUpdateDto("item2", 20000, 30);
    itemRepository.update(itemId, updateParam);

    //then
    Item findItem = itemRepository.findById(itemId).get();
    assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
    assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
    assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
  }

  @Test
  void findItems() {
    //given
    Item item1 = new Item("itemA-1", 10000, 10);
    Item item2 = new Item("itemA-2", 20000, 20);
    Item item3 = new Item("itemB-1", 30000, 30);

    itemRepository.save(item1);
    itemRepository.save(item2);
    itemRepository.save(item3);

    //둘 다 없음 검증
    test(null, null, item1, item2, item3);
    test("", null, item1, item2, item3);

    //itemName 검증
    test("itemA", null, item1, item2);
    test("itemA", null, item1, item2);
    test("itemB", null, item3);

    //maxPrice 검증
    test(null, 10000, item1);

    //둘 다 있음 검증
    test("itemA", 10000, item1);
  }

  void test(String itemName, Integer maxPrice, Item... items) {
    List<Item> result = itemRepository.findAll(new ItemSearchCond(itemName, maxPrice));
    assertThat(result).containsExactly(items);
  }
}
