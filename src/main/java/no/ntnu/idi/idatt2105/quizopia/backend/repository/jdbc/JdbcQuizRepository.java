package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizzesCreatedByUserDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;
import no.ntnu.idi.idatt2105.quizopia.backend.model.RefreshToken;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuizRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcQuizRepository implements QuizRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcQuizRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Quiz save(Quiz quiz) {
        String sql = "INSERT INTO quiz (title, description, is_public, created_at, template_id, category_id, media_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, quiz.getTitle());
            ps.setString(2, quiz.getDescription());
            ps.setBoolean(3, quiz.getIsPublic());
            ps.setObject(4, quiz.getCreatedAt());
            ps.setLong(5, quiz.getTemplateId());
            ps.setLong(6, quiz.getCategoryId());
            ps.setLong(7, quiz.getMedia_id());
            return ps;
        }, keyHolder);
        
        // Retrieve the generated primary key
        @SuppressWarnings("null")
        long generatedId = keyHolder.getKey().longValue();
        
        // Update the quiz object with the generated primary key
        quiz.setQuizId(generatedId);
        
        return quiz;
    }

    @Override
    public List<QuizzesCreatedByUserDto> findQuizzesByCreatorId(Long user_id) {
        String sql = "SELECT q.quiz_id, q.title AS quiz_title, q.media_id, m.file_path AS thumbnail_filepath " +
                     "FROM quiz q " +
                     "JOIN collaborators c ON q.quiz_id = c.quiz_id " +
                     "JOIN multi_medias m ON q.media_id = m.media_id " +
                     "WHERE c.user_id = ?";

        return jdbcTemplate.query(sql, new Object[]{user_id}, (rs, rowNum) -> new QuizzesCreatedByUserDto(
                rs.getLong("quiz_id"),
                rs.getString("quiz_title"),
                rs.getLong("media_id"),
                rs.getString("thumbnail_filepath")
        ));
    }
}