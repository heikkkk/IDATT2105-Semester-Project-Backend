package no.ntnu.idi.idatt2105.quizopia.backend.repository;

import java.util.List;

import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizInfoDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;

public interface QuizRepository {
    Quiz save(Quiz quiz);

    List<QuizInfoDto> findQuizzesByCreatorId(Long user_id);

    List<QuizInfoDto> findPublicQuizzes();

    List<QuizInfoDto> findQuizzesByCategoryName(String category);

    Quiz findQuizById(Long quiz_id);

    Quiz update(Quiz quiz);

    List<QuizInfoDto> findQuizzesByKeyword(String keyword);

    List<QuizInfoDto> findQuizzesByKeywordAndCategory(String keyword, String category);

    List<QuizInfoDto> findQuizzesByKeywordAndAuthor(String keyword, String author);

}
