package no.ntnu.idi.idatt2105.quizopia.backend.repository;

import no.ntnu.idi.idatt2105.quizopia.backend.model.QuizQuestion;

public interface QuizQuestionRepository {
    int save(QuizQuestion quizQuestion);
}
