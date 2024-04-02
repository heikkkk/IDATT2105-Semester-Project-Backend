package no.ntnu.idi.idatt2105.quizopia.backend.service;

import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizDtoMapper {

  public QuizDto toQuizDto(Quiz quiz) {
    QuizDto quizDto = new QuizDto();
    quizDto.setQuiz_id(quiz.getQuizId());
    quizDto.setTitle(quiz.getTitle());
    quizDto.setDescription(quiz.getDescription());
    quizDto.setIsPublic(quiz.getIsPublic());
    quizDto.setCreated_at(quiz.getCreatedAt()); 
    quizDto.setCategoryId(quiz.getCategoryId());
    quizDto.setTemplateId(quiz.getTemplateId());
    quizDto.setMediaId(quiz.getMedia_id());
    return quizDto;
  }
}
