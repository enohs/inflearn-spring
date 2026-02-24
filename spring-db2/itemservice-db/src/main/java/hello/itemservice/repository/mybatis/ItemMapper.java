package hello.itemservice.repository.mybatis;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

// 마이바티스 매핑 XML 호출해주는 매퍼 인터페이스
// 인터페이스 메서드 호출하면 XML의 해당 SQL을 실행하고 결과로 돌려줌
// @Mapper가 있어야 스프링 모듈이 인식하고 자체적으로 구현체를 만들어줌
@Mapper
public interface ItemMapper {

  void save(Item item);

  // 파라미터 2개 이상 넘어가면 @Param 필요
  void update(@Param("id") Long id, @Param("updateParam") ItemUpdateDto updateParam);

  List<Item> findAll(ItemSearchCond itemSearch);

  Optional<Item> findById(Long id);

}
