package no.ntnu.idi.idatt2105.quizopia.backend.service;

import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuestionsDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Questions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionMapper {

  public Questions toQuestion(QuestionsDto questionsDto) {
    Questions questions = new Questions();
    questions.setQuestionName(questionsDto.getQuestionName());
    questions.setQuestionText(questionsDto.getQuestionText());
    questions.setExplanations(questionsDto.getExplanations());
    questions.setPublic(questionsDto.getIsPublic());
    questions.setType_id(questionsDto.getType_id());
    questions.setDifficultyId(questionsDto.getDifficultyId());
    questions.setMediaId(questionsDto.getMediaId());
    questions.setQuestion_duration(questionsDto.getQuestion_duration());
    return questions;
  }
}
