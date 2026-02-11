package hello.itemservice.domain.item;

import lombok.Data;

// 너무 많은 걸 해줘서 오히려 예측하지 못한 일이 생길 수 있어서 위험함.
// 핵심 도메인 모델에서 사용하지 않기
// DTO에서는 적절하게 써도 괜찮음
@Data
public class Item {

  private Long id;
  private String itemName;
  private Integer price;
  private int quantity;

  public Item() {
  }

  public Item(String itemName, Integer price, int quantity) {
    this.itemName = itemName;
    this.price = price;
    this.quantity = quantity;
  }
}
