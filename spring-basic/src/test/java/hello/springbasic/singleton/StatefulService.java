package hello.springbasic.singleton;

public class StatefulService {
  // 싱글톤 내부에서는 공유 필드를 주의해야 한다 (무상태로 설계해야 함)
  private int price; // 상태를 유지하는 필드

  public void order(String name, int price) {
    System.out.println("name = " + name + " price = " + price);
    this.price = price; // 여기가 문제! (싱글톤 내부에서 공유 필드를 변경하면 안 된다)
    // return price; // 처럼 값을 저장하지 않고 바로 반환하도록 하는 것도 방법
  }

  public int getPrice() {
    return price;
  }

}
