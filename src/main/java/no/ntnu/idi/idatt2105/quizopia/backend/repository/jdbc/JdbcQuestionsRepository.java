package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

import no.ntnu.idi.idatt2105.quizopia.backend.model.Questions;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuestionsRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcQuestionsRepository implements QuestionsRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcQuestionsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Questions save(Questions questions) {
        String sql = "INSERT INTO questions (question_name, question_text, explanations, question_duration, is_public, type_id, difficulty_id, media_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, questions.getQuestionName());
            ps.setString(2, questions.getQuestionText());
            ps.setString(3, questions.getExplanations());
            ps.setInt(4, questions.getQuestion_duration());
            ps.setBoolean(5, questions.getPublic());
            ps.setLong(6, questions.getType_id());
            ps.setLong(7, questions.getDifficultyId());
            ps.setLong(8, questions.getMediaId());
            return ps;
        }, keyHolder);
        
        // Retrieve the generated primary key
        long generatedId = keyHolder.getKey().longValue();
        
        // Update the quiz object with the generated primary key
        questions.setQuestionId(generatedId);
        
        return questions;
    }

    @Override
    public Optional<Questions> findQuestionByAttributes(Questions questions) {
        String sql = """
                        SELECT * FROM questions 
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