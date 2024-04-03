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
    question.setPublic(questionDto.getIsPublic());
    question.setTypeId(questionDto.gettypeId());
    question.setDifficultyId(questionDto.getDifficultyId());
    question.setMediaId(questionDto.getMediaId());
    question.setQuestion_duration(questionDto.getQuestion_duration());
    return question;
  }
}
