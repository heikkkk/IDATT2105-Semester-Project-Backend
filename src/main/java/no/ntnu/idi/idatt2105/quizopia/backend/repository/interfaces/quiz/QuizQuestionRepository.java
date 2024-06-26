package no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz;

import java.util.List;

import no.ntnu.idi.idatt2105.quizopia.backend.model.QuizQuestion;

public interface QuizQuestionRepository {
    int save(QuizQuestion quizQuestion);

    List<Long> getQuestionIdByQuiz(Long quizId);

    int delete(Long quizId, Long questionId);

    int deleteQuizById(Long quizId);


}
