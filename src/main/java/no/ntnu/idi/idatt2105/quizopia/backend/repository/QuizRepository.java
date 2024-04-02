package no.ntnu.idi.idatt2105.quizopia.backend.repository;

import java.util.List;

import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizzesCreatedByUserDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;

public interface QuizRepository {
    Quiz save(Quiz quiz);

    List<QuizzesCreatedByUserDto> findQuizzesByCreatorId(Long user_id);

    List<QuizzesCreatedByUserDto> findPublicQuizzes();
}
