package no.ntnu.idi.idatt2105.quizopia.backend.repository;

import no.ntnu.idi.idatt2105.quizopia.backend.model.QuizQuestions;

public interface QuizQuestionsRepository {
    int save(QuizQuestions quizQuestions);
}
