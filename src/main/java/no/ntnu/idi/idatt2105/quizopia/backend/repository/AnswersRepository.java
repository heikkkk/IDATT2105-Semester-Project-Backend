package no.ntnu.idi.idatt2105.quizopia.backend.repository;

import no.ntnu.idi.idatt2105.quizopia.backend.model.Answers;

public interface AnswersRepository {
    Answers save(Answers answers);
}