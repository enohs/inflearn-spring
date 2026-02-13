package hello.validation.domain.item;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
// ObjectError를 아래 어노테이션으로 처리하기엔 기능이 빈약함. 차라리 코드로 직접 명시해주는 게 좋다
//@ScriptAssert(lang = "javascript", script = "_this.price * _this.quantity >= 10000", message = "총합이 10000원을 넘겨야 합니다.")
public class Item {

  //@NotNull(groups = UpdateCheck.class)
  private Long id;

  //@NotBlank(message = "공백X", groups = {SaveCheck.class, UpdateCheck.class})
  private String itemName;

  //@NotNull(groups = {SaveCheck.class, UpdateCheck.class})
  //@Range(min = 1000, max = 1000000, groups = {SaveCheck.class, UpdateCheck.class})
  private Integer price;

  //@NotNull(groups = {SaveCheck.class, UpdateCheck.class})
  //@Max(value = 9999, groups = SaveCheck.class)
  private Integer quantity;

  public Item() {
  }

  public Item(String itemName, Integer price, Integer quantity) {
    this.itemName = itemName;
    this.price = price;
    this.quantity = quantity;
  }
}
