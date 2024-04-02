package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import no.ntnu.idi.idatt2105.quizopia.backend.model.AnswersQuestions;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.AnswersQuestionsRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAnswersQuestionsRepository implements AnswersQuestionsRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAnswersQuestionsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(AnswersQuestions answersQuestions) {
        String sql = "INSERT INTO answers_questions (question_id, answer_id, is_correct) VALUES (?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            answersQuestions.getQuestionId(), answersQuestions.getAnswerId(), answersQuestions.getCorrect());
    }
}