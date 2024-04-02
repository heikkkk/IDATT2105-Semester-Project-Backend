package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizDto;
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

    @Override
    public List<QuizzesCreatedByUserDto> findPublicQuizzes() {
        String sql = "SELECT q.quiz_id, q.title AS quiz_title, q.media_id, m.file_path AS thumbnail_filepath " +
                     "FROM quiz q " +
                     "JOIN multi_medias m ON q.media_id = m.media_id " +
                     "WHERE q.is_public = 1 " +
                     "ORDER BY q.created_at DESC " + 
                     "LIMIT 24";

        return jdbcTemplate.query(sql, new Object[]{}, (rs, rowNum) -> new QuizzesCreatedByUserDto(
            rs.getLong("quiz_id"),
            rs.getString("quiz_title"),
            rs.getLong("media_id"),
            rs.getString("thumbnail_filepath")
        ));
    }

    @Override
    public List<QuizzesCreatedByUserDto> findQuizzesByCategoryName(String category) {
        String sql = "SELECT q.quiz_id, q.title AS quiz_title, q.media_id, m.file_path AS thumbnail_filepath " +
                     "FROM quiz q " +
                     "JOIN multi_medias m ON q.media_id = m.media_id " +
                     "JOIN categories c ON q.category_id = c.category_id " +
                     "WHERE c.name = ? AND q.is_public = 1 " +
                     "ORDER BY q.created_at DESC "; 
        return jdbcTemplate.query(sql, new Object[]{category}, (rs, rowNum) -> new QuizzesCreatedByUserDto(
            rs.getLong("quiz_id"),
            rs.getString("quiz_title"),
            rs.getLong("media_id"),
            rs.getString("thumbnail_filepath")
        ));             
    }

    @Override
public Quiz findQuizById(Long quizId) {
    String sql = "SELECT * FROM quiz WHERE quiz_id = ?";

    return jdbcTemplate.queryForObject(sql, new Object[]{quizId}, (rs, rowNum) -> {
        Quiz quiz = new Quiz();
        quiz.setQuizId(rs.getLong("quiz_id"));
        quiz.setTitle(rs.getString("title"));
        quiz.setDescription(rs.getString("description"));
        quiz.setIsPublic(rs.getBoolean("is_public"));
        quiz.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        quiz.setTemplateId(rs.getLong("template_id"));
        quiz.setCategoryId(rs.getLong("category_id"));
        quiz.setMedia_id(rs.getLong("media_id"));
        return quiz;
    });
}



}