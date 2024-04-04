package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.quiz;

import no.ntnu.idi.idatt2105.quizopia.backend.model.QuizQuestion;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz.QuizQuestionRepository;

import java.util.List;

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

    @Override
    public List<Long> getQuestionIdByQuiz(Long quizId) {
        String sql = "SELECT question_id FROM quiz_question WHERE quiz_id = ?";
        return jdbcTemplate.query(sql, new Object[]{quizId}, (rs, rowNum) -> rs.getLong("question_id"));
    }

    @Override
    public int delete(Long quizId, Long questionId) {
        String sql = "DELETE FROM quiz_question WHERE quiz_id = ? AND question_id = ?";
        return jdbcTemplate.update(sql, quizId, questionId);
    }

    @Override
    public int deleteQuizById(Long quiz_id) {
        String sql = "DELETE FROM quiz_question WHERE quiz_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, quiz_id);
        return rowsAffected;
    }
}