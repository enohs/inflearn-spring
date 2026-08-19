package hello.springadv1.aop.exam;

import hello.springadv1.aop.exam.annotation.Trace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {

  private final ExamRepository examRepository;

  @Trace
  public void request(String itemId) {
    examRepository.save(itemId);
  }

}
