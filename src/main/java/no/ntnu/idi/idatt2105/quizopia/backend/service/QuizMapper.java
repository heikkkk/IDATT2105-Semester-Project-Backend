package no.ntnu.idi.idatt2105.quizopia.backend.service;

import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizMapper {

  public Quiz toQuiz(QuizDto quizDto) {
    Quiz quiz = new Quiz();
    quiz.setTitle(quizDto.getTitle());
    quiz.setDescription(quizDto.getDescription());
    quiz.setIsPublic(quizDto.getIsPublic());
    quiz.setCreatedAt(quizDto.getCreated_at()); 
    quiz.setCategoryId(quizDto.getCategoryId());
    quiz.setTemplateId(quizDto.getTemplateId());
    quiz.setMediaId(quizDto.getMediaId());
    return quiz;
  }
}
