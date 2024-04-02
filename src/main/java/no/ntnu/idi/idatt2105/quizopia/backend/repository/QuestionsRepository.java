package no.ntnu.idi.idatt2105.quizopia.backend.repository;

import java.util.List;

import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuestionsDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Questions;

public interface QuestionsRepository {
    Questions save(Questions questions);

    List<Questions> findQuestionsByQuizId(Long quiz_id);

}