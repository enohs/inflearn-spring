package hello.springtx.propagation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

// 데이터베이스에 남기는 로그
@Entity
@Getter
@Setter
public class Log {

  @Id
  @GeneratedValue
  private Long id;

  private String message;

  public Log() {
  }

  public Log(String message) {
    this.message = message;
  }

}
