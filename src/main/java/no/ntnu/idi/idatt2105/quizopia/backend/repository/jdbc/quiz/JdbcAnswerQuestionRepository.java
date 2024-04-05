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

    @Override
    public int save(AnswerQuestion answerQuestion) {
        String sql = "INSERT INTO answer_question (question_id, answer_id, is_correct) VALUES (?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            answerQuestion.getQuestionId(), answerQuestion.getAnswerId(), answerQuestion.getIsCorrect());
    }

    @Override
    public List<Long> getAnswerIdByQuestionId(Long questionId) {
        String sql = "SELECT answer_id FROM answer_question WHERE question_id = ?";
        return jdbcTemplate.query(sql, 
            ps -> ps.setLong(1, questionId), 
            (rs, rowNum) -> rs.getLong("answer_id")); 
    }


    @Override
    public int delete(Long questionId, Long answerId) {
        String sql = "DELETE FROM answer_question WHERE question_id = ? AND answer_id = ?";
        return jdbcTemplate.update(sql, questionId, answerId);
    }
}