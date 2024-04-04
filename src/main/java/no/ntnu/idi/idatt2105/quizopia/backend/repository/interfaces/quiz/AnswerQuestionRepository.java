package no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz;

import java.util.List;

import no.ntnu.idi.idatt2105.quizopia.backend.model.AnswerQuestion;

public interface AnswerQuestionRepository {
    int save(AnswerQuestion answerQuestion);

    List<Long> getAnswerIdByQuestionId(Long questionId);

    int delete(Long questionId, Long answerId);

}
