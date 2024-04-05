package no.ntnu.idi.idatt2105.quizopia.backend.service.quiz;

import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuestionDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Question;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionDtoMapper {

  public QuestionDto toQuestionDto(Question question) {
    QuestionDto questionDto = new QuestionDto();
    questionDto.setQuestionId(question.getQuestionId());
    questionDto.setQuestionName(question.getQuestionName());
    questionDto.setQuestionText(question.getQuestionText());
    questionDto.setExplanations(question.getExplanations());
    questionDto.setIsPublic(question.getIsPublic());
    questionDto.setTypeId(question.getTypeId());
    questionDto.setDifficultyId(question.getDifficultyId());
    questionDto.setMediaId(question.getMediaId());
    questionDto.setQuestionDuration(question.getQuestionDuration());
    return questionDto;
  }
}
