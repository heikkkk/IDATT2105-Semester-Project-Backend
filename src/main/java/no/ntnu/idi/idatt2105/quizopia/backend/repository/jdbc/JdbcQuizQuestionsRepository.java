package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import no.ntnu.idi.idatt2105.quizopia.backend.model.QuizQuestions;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuizQuestionsRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcQuizQuestionsRepository implements QuizQuestionsRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcQuizQuestionsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(QuizQuestions quizQuestions) {
        String sql = "INSERT INTO Quiz_Questions (quiz_id, question_id) VALUES (?, ?)";
        return jdbcTemplate.update(
            sql,
            quizQuestions.getQuizId(), quizQuestions.getQuestionId());
    }
}