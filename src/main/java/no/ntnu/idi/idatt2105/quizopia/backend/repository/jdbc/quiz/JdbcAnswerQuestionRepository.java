package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.quiz;

import no.ntnu.idi.idatt2105.quizopia.backend.model.AnswerQuestion;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz.AnswerQuestionRepository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAnswerQuestionRepository implements AnswerQuestionRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAnswerQuestionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Saves an AnswerQuestion entity to the database.
     * 
     * @param answerQuestion the AnswerQuestion entity you want to save.
     * @return the number of rows affected.
     */
    @Override
    public int save(AnswerQuestion answerQuestion) {
        String sql = "INSERT INTO answer_question (question_id, answer_id, is_correct) VALUES (?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            answerQuestion.getQuestionId(), answerQuestion.getAnswerId(), answerQuestion.getIsCorrect());
    }

    /**
     * Retrieves a list of answer IDs associated with a given question ID.
     * 
     * @param questionId the ID of the question.
     * @return a list of answer IDs.
     */
    @Override
    public List<Long> getAnswerIdByQuestionId(Long questionId) {
        String sql = "SELECT answer_id FROM answer_question WHERE question_id = ?";
        return jdbcTemplate.query(sql, 
            ps -> ps.setLong(1, questionId), 
            (rs, rowNum) -> rs.getLong("answer_id")); 
    }


    /**
     * Deletes an AnswerQuestion mapping from the database based on 
     * a question ID and an answer ID.
     * 
     * @param questionId the ID of the question.
     * @param answerId the ID of the answer.
     * @return the number of rows affected.
     */
    @Override
    public int delete(Long questionId, Long answerId) {
        String sql = "DELETE FROM answer_question WHERE question_id = ? AND answer_id = ?";
        return jdbcTemplate.update(sql, questionId, answerId);
    }
}