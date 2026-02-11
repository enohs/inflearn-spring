package hello.springbasic.singleton;

public class SingletonService {

  // static이므로 자바 실행 시 바로 자기 자신을 생성해서 참조에 넣어둠
  private static final SingletonService instance = new SingletonService();

  // 참조를 가져올 수 있는 유일한 통로
  // 항상 같은 참조를 반환한다
  public static SingletonService getInstance() {
    return instance;
  }

  // 외부에서 new 키워드로 추가적인 인스턴스를 생성하지 못하게 막아둠
  // 추가로 생성할 수 있는 방법은 이제 없다!
  private SingletonService() {

  }

  public void logic() {
    System.out.println("싱글톤 객체 로직 호출");
  }
}
