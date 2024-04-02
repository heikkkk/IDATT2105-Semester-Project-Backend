package no.ntnu.idi.idatt2105.quizopia.backend.service;

import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuestionsDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Questions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionDtoMapper {

  public QuestionsDto toQuestionDto(Questions questions) {
    QuestionsDto questionsDto = new QuestionsDto();
    questionsDto.setQuestionName(questions.getQuestionName());
    questionsDto.setQuestionText(questions.getQuestionText());
    questionsDto.setExplanations(questions.getExplanations());
    questionsDto.setIsPublic(questions.getPublic());
    questionsDto.setType_id(questions.getType_id());
    questionsDto.setDifficultyId(questions.getDifficultyId());
    questionsDto.setMediaId(questions.getMediaId());
    questionsDto.setQuestion_duration(questions.getQuestion_duration());
    return questionsDto;
  }
}
