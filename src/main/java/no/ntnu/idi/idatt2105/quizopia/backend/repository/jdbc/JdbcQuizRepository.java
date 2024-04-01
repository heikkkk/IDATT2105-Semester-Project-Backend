package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import no.ntnu.idi.idatt2105.quizopia.backend.model.Questions;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuizRepository;

import java.util.Optional;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcQuizRepository implements QuizRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcQuizRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Quiz quiz) {
        String sql = "INSERT INTO Quiz (title, description, is_public, created_at, template_id, category_id, media_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            quiz.getTitle(), quiz.getDescription(), quiz.getIsPublic(), quiz.getCreatedAt(), quiz.getTemplateId(), quiz.getCategoryId(), quiz.getMedia_id());
    }

    @Override
    public Optional<Quiz> findQuizByAttributes(Quiz quiz) {
        String sql = """
                        SELECT * FROM Quiz 
                        WHERE title = ? AND description = ? AND is_public = ? 
                        AND created_at = ? AND template_id = ? AND category_id = ? 
                        AND media_id = ?
                        """;
        try {
            Quiz quizExisting = jdbcTemplate.queryForObject(
                sql,
                BeanPropertyRowMapper.newInstance(Quiz.class),
                quiz);
            return Optional.ofNullable(quizExisting);
            } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }
}