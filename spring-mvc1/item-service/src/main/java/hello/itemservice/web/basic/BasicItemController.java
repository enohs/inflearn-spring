package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

  private final ItemRepository itemRepository;

  @GetMapping
  public String items(Model model) {
    List<Item> items = itemRepository.findAll();
    model.addAttribute("items", items);
    return "basic/items";
  }

  @GetMapping("/{itemId}")
  public String item(@PathVariable Long itemId, Model model) {
    Item item = itemRepository.findById(itemId);
    model.addAttribute("item", item);
    return "basic/item";
  }

  @GetMapping("/add")
  public String addForm() {
    return "basic/addForm";
  }

  //  @PostMapping("/add")
  public String addItemV1(
      @RequestParam String itemName,
      @RequestParam int price,
      @RequestParam int quantity,
      Model model) {
    Item item = new Item(itemName, price, quantity);
    itemRepository.save(item);

    model.addAttribute("item", item);
    return "basic/item";
  }

  //  @PostMapping("/add")
  // @ModelAttribute에 이름값을 넣어주면 해당 이름을 가지고 자동으로 모델에도 넣어준다
  public String addItemV2(@ModelAttribute("item") Item item, Model model) {
    itemRepository.save(item);
    //model.addAttribute("item", item); // 자동 추가되어 생략 가능
    return "basic/item";
  }

  //  @PostMapping("/add")
  // @ModelAttribute에서 이름값을 생략하면 객체 이름을 소문자로 설정하여 모델 어트리뷰트에 담는다 (ex) Item -> item
  public String addItemV3(@ModelAttribute Item item) {
    itemRepository.save(item);
    return "basic/item";
  }

  // @ModelAttribute를 지워도 인식하며, 객체에 쿼리 스트링을 담을뿐 아니라 객체의 이름을 소문자로 하여 모델에도 담아준다
//  @PostMapping("/add")
  public String addItemV4(Item item) {
    itemRepository.save(item);
    return "basic/item";
  }

  // 이전의 메서드는 새로고침 시 또다시 Post가 동작하는 문제가 있었기에
  // Post/Redirect/Get (PRG) 적용
//  @PostMapping("/add")
  public String addItemV5(Item item) {
    itemRepository.save(item);
    return "redirect:/basic/items/" + item.getId();
  }

  @PostMapping("/add")
  public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
    Item savedItem = itemRepository.save(item);
    redirectAttributes.addAttribute("itemId", savedItem.getId());
    redirectAttributes.addAttribute("status", true);
    // redirectAttributes에 담은 어트리뷰트 이름을 경로 변수로 사용 가능하고
    // 사용되지 않은 어트리뷰트는 자동으로 쿼리 파라미터로 전달된다
    return "redirect:/basic/items/{itemId}";
  }

  @GetMapping("/{itemId}/edit")
  public String editForm(@PathVariable Long itemId, Model model) {
    Item item = itemRepository.findById(itemId);
    model.addAttribute("item", item);
    return "basic/editForm";
  }

  @PostMapping("/{itemId}/edit")
  public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
    itemRepository.update(itemId, item);
    return "redirect:/basic/items/{itemId}";
  }


  /**
   * 테스트용 데이터 추가
   */
  @PostConstruct
  public void init() {
    itemRepository.save(new Item("itemA", 10000, 10));
    itemRepository.save(new Item("itemB", 20000, 20));
  }

}
