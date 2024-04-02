package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import no.ntnu.idi.idatt2105.quizopia.backend.model.AnswerQuestion;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.AnswerQuestionRepository;

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
            answerQuestion.getQuestionId(), answerQuestion.getAnswerId(), answerQuestion.getCorrect());
    }
}