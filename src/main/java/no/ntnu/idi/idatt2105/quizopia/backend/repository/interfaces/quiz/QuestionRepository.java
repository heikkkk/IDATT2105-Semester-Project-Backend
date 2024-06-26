package no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz;

import java.util.List;

import no.ntnu.idi.idatt2105.quizopia.backend.model.Question;

public interface QuestionRepository {
    Question save(Question question);

    List<Question> findQuestionByQuizId(Long quizId);

}