package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import no.ntnu.idi.idatt2105.quizopia.backend.model.QuizQuestion;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuizQuestionRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcQuizQuestionRepository implements QuizQuestionRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcQuizQuestionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(QuizQuestion quizQuestion) {
        String sql = "INSERT INTO quiz_question (quiz_id, question_id) VALUES (?, ?)";
        return jdbcTemplate.update(
            sql,
            quizQuestion.getQuizId(), quizQuestion.getQuestionId());
    }
}