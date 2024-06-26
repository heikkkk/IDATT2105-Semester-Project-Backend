package no.ntnu.idi.idatt2105.quizopia.backend.service.quiz;

import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class QuizMapper {

  public Quiz toQuiz(QuizDto quizDto) {
    Quiz quiz = new Quiz();
    quiz.setTitle(quizDto.getTitle());
    quiz.setDescription(quizDto.getDescription());
    quiz.setPublic(quizDto.isPublic());
    if (quizDto.getCreatedAt() == null) {
      quiz.setCreatedAt(LocalDateTime.now());
    } else {
      quiz.setCreatedAt(quizDto.getCreatedAt()); 
    }
    quiz.setCategoryId(quizDto.getCategoryId());
    quiz.setTemplateId(quizDto.getTemplateId());
    quiz.setMediaId(quizDto.getMediaId());
    return quiz;
  }
}
