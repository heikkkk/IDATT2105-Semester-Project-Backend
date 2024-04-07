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

    /**
     * Retrieves question IDs associated with a given quiz ID.
     *
     * @param quizId the ID of the quiz.
     * @return a list of question IDs associated with the quiz.
     */
    @Override
    public int save(QuizQuestion quizQuestion) {
        String sql = "INSERT INTO quiz_question (quiz_id, question_id) VALUES (?, ?)";
        return jdbcTemplate.update(
            sql,
            quizQuestion.getQuizId(), quizQuestion.getQuestionId());
    }

    /**
     * Retrieves question IDs associated with a given quiz ID.
     *
     * @param quizId the ID of the quiz.
     * @return a list of the question IDs associated with the quiz.
     */
    @Override
    public List<Long> getQuestionIdByQuiz(Long quizId) {
        String sql = "SELECT question_id FROM quiz_question WHERE quiz_id = ?";
        return jdbcTemplate.query(sql, 
            ps -> ps.setLong(1, quizId), 
            (rs, rowNum) -> rs.getLong("question_id")); 
    }

     /**
     * Deletes a QuizQuestion association from the database.
     *
     * @param quizId the ID of the quiz.
     * @param questionId the ID of the question.
     * @return the number of rows affected.
     */
    @Override
    public int delete(Long quizId, Long questionId) {
        String sql = "DELETE FROM quiz_question WHERE quiz_id = ? AND question_id = ?";
        return jdbcTemplate.update(sql, quizId, questionId);
    }

    /**
     * Deletes all QuizQuestion associations for a given quiz ID.
     *
     * @param quizId the ID of the quiz to delete all associations for.
     * @return the number of rows affected.
     */
    @Override
    public int deleteQuizById(Long quizId) {
        String sql = "DELETE FROM quiz_question WHERE quiz_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, quizId);
        return rowsAffected;
    }
}