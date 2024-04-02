package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import java.sql.PreparedStatement;
import java.sql.Statement;

import no.ntnu.idi.idatt2105.quizopia.backend.model.Questions;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuestionsRepository;
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
        @SuppressWarnings("null")
        long generatedId = keyHolder.getKey().longValue();
        
        // Update the quiz object with the generated primary key
        questions.setQuestionId(generatedId);
        
        return questions;
    }
}