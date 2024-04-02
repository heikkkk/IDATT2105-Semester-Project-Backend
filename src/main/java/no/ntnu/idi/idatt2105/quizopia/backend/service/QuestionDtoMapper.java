package no.ntnu.idi.idatt2105.quizopia.backend.service;

import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuestionDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Question;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionDtoMapper {

  public QuestionDto toQuestionDto(Question question) {
    QuestionDto questionDto = new QuestionDto();
    questionDto.setquestionId(question.getQuestionId());
    questionDto.setQuestionName(question.getQuestionName());
    questionDto.setQuestionText(question.getQuestionText());
    questionDto.setExplanations(question.getExplanations());
    questionDto.setIsPublic(question.getPublic());
    questionDto.settypeId(question.getTypeId());
    questionDto.setDifficultyId(question.getDifficultyId());
    questionDto.setMediaId(question.getMediaId());
    questionDto.setQuestion_duration(question.getQuestion_duration());
    return questionDto;
  }
}
