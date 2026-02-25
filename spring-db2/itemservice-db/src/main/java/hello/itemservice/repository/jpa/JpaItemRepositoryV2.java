package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

// 컴포넌트 스캔 + 예외 변환 AOP 적용 대상. 그런데 Spring data jpa를 사용하면 이 어노테이션 여부에 상관없이 예외가 변환됨
@Repository
@Transactional
@RequiredArgsConstructor
public class JpaItemRepositoryV2 implements ItemRepository {

  private final SpringDataJpaItemRepository repository;

  @Override
  public Item save(Item item) {
    return repository.save(item);
  }

  // 트랜잭션 커밋 시점에 변경 내용 반영
  @Override
  public void update(Long itemId, ItemUpdateDto updateParam) {
    Item findItem = repository.findById(itemId).orElseThrow();
    findItem.setItemName(updateParam.getItemName());
    findItem.setPrice(updateParam.getPrice());
    findItem.setQuantity(updateParam.getQuantity());
  }

  @Override
  public Optional<Item> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public List<Item> findAll(ItemSearchCond cond) {
    String itemName = cond.getItemName();
    Integer maxPrice = cond.getMaxPrice();

    // 실무에서는 이렇게 안 함
    if (StringUtils.hasText("%" + itemName + "%") && maxPrice != null) {
      //return repository.findByItemNameLikeAndPriceLessThanEqual(itemName, maxPrice);
      return repository.findItems("%" + itemName + "%", maxPrice);
    } else if (StringUtils.hasText(itemName)) {
      return repository.findByItemNameLike("%" + itemName + "%");
    } else if (maxPrice != null) {
      return repository.findByPriceLessThanEqual(maxPrice);
    } else {
      return repository.findAll();
    }
  }

}
