package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import java.util.Optional;

import no.ntnu.idi.idatt2105.quizopia.backend.model.Questions;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuestionsRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcQuestionsRepository implements QuestionsRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcQuestionsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Questions questions) {
        String sql = "INSERT INTO Questions (question_name, question_text, explanations, question_duration, is_public, type_id, difficulty_id, media_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            questions.getQuestionName(), questions.getQuestionText(), questions.getExplanations(), questions.getQuestion_duration(), questions.getPublic(), questions.getType_id(), questions.getDifficultyId(), questions.getMediaId());
    }

    @Override
    public Optional<Questions> findQuestionByAttributes(Questions questions) {
        String sql = """
                        SELECT * FROM Questions 
                        WHERE question_name = ? AND question_text = ? AND explanations = ? 
                        AND is_public = ? AND type_id = ? AND difficulty_id = ? 
                        AND media_id = ? AND question_duration = ?
                        """;
        try {
            Questions questionsExisting = jdbcTemplate.queryForObject(
                sql,
                BeanPropertyRowMapper.newInstance(Questions.class),
                questions);
            return Optional.ofNullable(questionsExisting);
            } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long count() {
        Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Questions", Long.class);
        return Optional.ofNullable(count).orElse(0L);
    }
}