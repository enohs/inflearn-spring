package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


// 예외 처리 알아서 해줌(?)
// 따로 HandlerExceptionResolver에 등록 안 해도 400 에러로 처리가 됨
// 정확히는 ResponseStatusExceptionResolver가 훑어보고 처리해주는 것
// 해당 ExceptionResolver 코드를 보면 response.sendError를 호출하기 때문에 서블릿 컨테이너에서 다시 BasicErrorController를 향해 /error를 호출한다
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")// reason에 문자 말고 properties 값을 넣을 수도 있다
public class BadRequestException extends RuntimeException {

}
