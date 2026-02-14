package hello.exception.api;

import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
public class ApiExController {

  @GetMapping("/api/members/{id}")
  public MemberDto getMember(@PathVariable String id) {
    if (id.equals("ex")) {
      throw new RuntimeException("잘못된 사용자");
    }

    if (id.equals("bad")) {
      throw new IllegalArgumentException("잘못된 값 입력");
    }

    if (id.equals("user-ex")) {
      throw new UserException("사용자 오류");
    }

    return new MemberDto(id, "hello");
  }

  @GetMapping("/api/response-status-ex1")
  public String responseStatusEx1() {
    throw new BadRequestException();
  }

  // ResponseStatusException도 ResponseStatusExceptionResolver가 처리해서 원하는 상태값으로 반환가능
  @GetMapping("/api/response-status-ex2")
  public String responseStatusEx2() {
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());
  }

  // 파라미터 타입 오류 같은 건 대부분 클라이언트 측에서 값을 잘못 보낸 경우이기 때문에 DefaultHandlerExceptionResolver가 400번 오류로 변경해서 반환해줌
  @GetMapping("/api/default-handler-ex")
  public String defaultException(@RequestParam Integer data) {
    return "ok";
  }

  @Data
  @AllArgsConstructor
  static class MemberDto {

    private String memberId;
    private String name;
  }
}
