package no.ntnu.idi.idatt2105.quizopia.backend.service.quiz;

import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuestionDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Question;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionMapper {

  public Question toQuestion(QuestionDto questionDto) {
    Question question = new Question();
    question.setQuestionName(questionDto.getQuestionName());
    question.setQuestionText(questionDto.getQuestionText());
    question.setExplanations(questionDto.getExplanations());
    question.setIsPublic(questionDto.getIsPublic());
    question.setTypeId(questionDto.getTypeId());
    question.setDifficultyId(questionDto.getDifficultyId());
    question.setMediaId(questionDto.getMediaId());
    question.setQuestionDuration(questionDto.getQuestionDuration());
    return question;
  }
}
