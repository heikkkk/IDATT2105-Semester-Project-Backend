package no.ntnu.idi.idatt2105.quizopia.backend.repository;

import java.util.List;

import no.ntnu.idi.idatt2105.quizopia.backend.dto.AnswersDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Answers;

public interface AnswersRepository {
    Answers save(Answers answers);

    List<AnswersDto> findAnswersByQuestionId(Long questions_id);

}