package no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz;

import java.util.List;

import no.ntnu.idi.idatt2105.quizopia.backend.dto.AnswerDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Answer;

public interface AnswerRepository {
    Answer save(Answer answer);

    List<AnswerDto> findAnswerByQuestionId(Long questionId);

}