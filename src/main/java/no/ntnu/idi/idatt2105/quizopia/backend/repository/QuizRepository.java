package no.ntnu.idi.idatt2105.quizopia.backend.repository;

import java.util.Optional;

import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;

public interface QuizRepository {
    int save(Quiz quiz);

    Optional<Quiz> findQuizByAttributes(Quiz quiz);
}
