package no.ntnu.idi.idatt2105.quizopia.backend.repository;

import java.util.Optional;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Questions;

public interface QuestionsRepository {
    Questions save(Questions questions);

    Optional<Questions> findQuestionByAttributes(Questions questions);

    Long count();
}