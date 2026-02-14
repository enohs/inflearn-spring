package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
// 해당 어노테이션이 있으면 컨트롤러에서 에러 처리와 로직을 분리할 수 있다
// 대상을 적용하지 않으면 전체 컨트롤러에서 발생하는 오류를 이곳에서 처리해줌
// 대상의 경우 어노테이션, 패키지, 클래스를 대상으로 할 수 있음
// 이전에 학습한 오류 처리를 테스트하려면 주석처리 필요
@RestControllerAdvice
public class ExControllerAdvice {

  // 다른 컨트롤러에서 해당 에러가 발생하면 아래 로직이 실행 되면서 Json으로 반환
  // DispatcherServlet에서 가장 먼저 물어보는 ExceptionResolver가
  // ExceptionHandlerExceptionResolver이기 때문에 해당 resolver가 @ExceptionHandler가 있는지 확인하고 실행해줌
  //-> WAS에 갔다가 다시 컨트롤러를 호출하지 않고 바로 정상흐름으로 응답하게 해줌
  // 근데, 정상흐름이 되어 200으로 응답이 오기 때문에 @ResponseStatus 설정 필요
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public ErrorResult illegalExHandler(IllegalArgumentException e) {
    log.error("[exceptionHandler] ex", e);
    return new ErrorResult("BAD", e.getMessage());
  }

  // exception을 어노테이션 말고 파라미터로 작성해줘도 동일하게 동작함
  // 아예 ResponseEntity를 사용해서 상태코드까지 한 번에 담아서 보냄
  @ExceptionHandler
  public ResponseEntity<ErrorResult> userExHandler(UserException e) {
    log.error("[exceptionHandler] ex", e);
    ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
    return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
  }

  // 참고로 위의 메서드에서 사용된 Exception들은 그 자식까지도 같이 처리해준다
  // 그런데도 처리되지 못한 게 있다면 이쪽으로 넘어오게 된다
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler
  public ErrorResult exHandler(Exception e) {
    log.error("[exceptionHandler] ex", e);
    return new ErrorResult("EX", "내부 오류");
  }

}
