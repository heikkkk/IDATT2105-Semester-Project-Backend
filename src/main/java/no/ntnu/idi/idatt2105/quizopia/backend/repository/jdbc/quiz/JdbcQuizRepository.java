package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.quiz;

import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizInfoDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz.QuizRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.sql.Types;

import org.springframework.dao.EmptyResultDataAccessException;
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
            
            ps.setObject(5, quiz.getTemplateId(), Types.BIGINT); 
            ps.setObject(6, quiz.getCategoryId(), Types.BIGINT);
            ps.setObject(7, quiz.getMediaId(), Types.BIGINT);
            return ps;
        }, keyHolder);
        
        long generatedId = keyHolder.getKey().longValue();
        
        quiz.setQuizId(generatedId);
        
        return quiz;
    }

    @Override
    public Quiz update(Quiz quiz) {
        String sql = "UPDATE quiz SET title = ?, description = ?, is_public = ?, created_at = ?, template_id = ?, category_id = ?, media_id = ? WHERE quiz_id = ?";
        
        int rowsAffected = jdbcTemplate.update(sql,
            quiz.getTitle(),
            quiz.getDescription(),
            quiz.getIsPublic(),
            quiz.getCreatedAt(),
            quiz.getTemplateId(),
            quiz.getCategoryId(),
            quiz.getMediaId(),
            quiz.getQuizId());
        if (rowsAffected > 0) {
            return quiz;
        } else {
            return null;
        }
    }


    @Override
    public List<QuizInfoDto> findQuizzesByCreatorId(Long userId) {
        String sql = "SELECT q.quiz_id, q.title AS quiz_title, q.category_id, u.username AS author, m.file_path AS thumbnail_filepath " +
                    "FROM quiz q " +
                    "JOIN collaborator c ON q.quiz_id = c.quiz_id " +
                    "JOIN user u ON c.user_id = u.user_id " +
                    "JOIN multi_media m ON q.media_id = m.media_id " +
                    "WHERE c.user_id = ?";

        return jdbcTemplate.query(sql,
            ps -> ps.setLong(1, userId),
            (rs, rowNum) -> new QuizInfoDto(
                rs.getLong("quiz_id"),
                rs.getString("quiz_title"),
                rs.getLong("category_id"),
                rs.getString("author"),
                rs.getString("thumbnail_filepath")
            )
        );
    }


    @Override
    public List<QuizInfoDto> findPublicQuizzes() {
        String sql = "SELECT q.quiz_id, q.title AS quiz_title, q.category_id, u.username AS author, m.file_path AS thumbnail_filepath " +
                    "FROM quiz q " +
                    "JOIN multi_media m ON q.media_id = m.media_id " +
                    "JOIN collaborator c ON q.quiz_id = c.quiz_id " +
                    "JOIN user u ON c.user_id = u.user_id " +
                    "WHERE q.is_public = 1 AND c.type_id = 1 " + // Assuming c.type_id = 1 signifies the author
                    "ORDER BY q.created_at DESC " +
                    "LIMIT 24";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new QuizInfoDto(
            rs.getLong("quiz_id"),
            rs.getString("quiz_title"),
            rs.getLong("category_id"),
            rs.getString("author"),
            rs.getString("thumbnail_filepath")
        ));
    }
    @Override
public List<QuizInfoDto> findQuizzesByCategoryName(String category) {
    String sql = "SELECT q.quiz_id, q.title AS quiz_title, q.category_id, u.username AS author, m.file_path AS thumbnail_filepath " +
                "FROM quiz q " +
                "JOIN multi_media m ON q.media_id = m.media_id " +
                "JOIN category c ON q.category_id = c.category_id " +
                "JOIN collaborator col ON q.quiz_id = col.quiz_id " +
                "JOIN user u ON col.user_id = u.user_id " +
                "WHERE c.name = ? AND q.is_public = 1 AND col.type_id = 1 " + // Assuming col.type_id = 1 signifies the author
                "ORDER BY q.created_at DESC ";

    return jdbcTemplate.query(sql, 
        ps -> ps.setString(1, category), // Correctly set the SQL parameter for the category name
        (rs, rowNum) -> new QuizInfoDto(
            rs.getLong("quiz_id"),
            rs.getString("quiz_title"),
            rs.getLong("category_id"),
            rs.getString("author"),
            rs.getString("thumbnail_filepath")
        )
    );
}


    @Override
    public Quiz findQuizById(Long quizId) {
        String sql = "SELECT * FROM quiz WHERE quiz_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Quiz quiz = new Quiz();
                quiz.setQuizId(rs.getLong("quiz_id"));
                quiz.setTitle(rs.getString("title"));
                quiz.setDescription(rs.getString("description"));
                quiz.setIsPublic(rs.getBoolean("is_public"));
                quiz.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                quiz.setTemplateId(rs.getLong("template_id"));
                quiz.setCategoryId(rs.getLong("category_id"));
                quiz.setMediaId(rs.getLong("media_id"));
                return quiz;
            }, quizId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public List<QuizInfoDto> findQuizzesByKeyword(String keyword) {
        String lowerCaseKeyword = "%" + keyword.toLowerCase() + "%";
        String sql = "SELECT q.quiz_id, q.title AS quiz_title, q.category_id, u.username AS author, m.file_path AS thumbnail_filepath " +
                    "FROM quiz q " +
                    "JOIN multi_media m ON q.media_id = m.media_id " +
                    "JOIN collaborator c ON q.quiz_id = c.quiz_id " +
                    "JOIN user u ON c.user_id = u.user_id " +
                    "WHERE LOWER(q.title) LIKE ? AND q.is_public = 1 " +
                    "AND c.type_id = 1 " + // Assumption: c.type_id = 1 signifies the author
                    "ORDER BY q.created_at DESC " +
                    "LIMIT 24";

        return jdbcTemplate.query(sql,
            ps -> ps.setString(1, lowerCaseKeyword),
            (rs, rowNum) -> new QuizInfoDto(
                rs.getLong("quiz_id"),
                rs.getString("quiz_title"),
                rs.getLong("category_id"),
                rs.getString("author"),
                rs.getString("thumbnail_filepath")
            )
        );
    }



    @Override
    public List<QuizInfoDto> findQuizzesByKeywordAndCategory(String keyword, String category) {
        String lowerCaseKeyword = "%" + keyword.toLowerCase() + "%";
        String lowerCaseCategory = category.toLowerCase();

        String sql = "SELECT q.quiz_id, q.title AS quiz_title, q.category_id, u.username AS author, m.file_path AS thumbnail_filepath " +
                    "FROM quiz q " +
                    "JOIN multi_media m ON q.media_id = m.media_id " +
                    "JOIN category c ON q.category_id = c.category_id " +
                    "JOIN collaborator col ON q.quiz_id = col.quiz_id " +
                    "JOIN user u ON col.user_id = u.user_id " +
                    "WHERE LOWER(c.name) = ? AND LOWER(q.title) LIKE ? AND q.is_public = 1 " +
                    "AND col.type_id = 1 " + 
                    "ORDER BY q.created_at DESC " +
                    "LIMIT 24";

        return jdbcTemplate.query(sql,
            ps -> {
                ps.setString(1, lowerCaseCategory);
                ps.setString(2, lowerCaseKeyword);
            },
            (rs, rowNum) -> new QuizInfoDto(
                rs.getLong("quiz_id"),
                rs.getString("quiz_title"),
                rs.getLong("category_id"),
                rs.getString("author"),
                rs.getString("thumbnail_filepath")
            )
        );
    }

    @Override
    public List<QuizInfoDto> findQuizzesByKeywordAndAuthor(String keyword, String author) {
        String lowerCaseKeyword = "%" + keyword.toLowerCase() + "%";
        String lowerCaseAuthor = author.toLowerCase();

        String sql = "SELECT q.quiz_id, q.title AS quiz_title, q.category_id, u.username AS author, m.file_path AS thumbnail_filepath " +
                    "FROM quiz q " +
                    "JOIN multi_media m ON q.media_id = m.media_id " +
                    "JOIN collaborator c ON q.quiz_id = c.quiz_id " +
                    "JOIN user u ON c.user_id = u.user_id " +
                    "WHERE LOWER(u.username) = ? AND c.type_id = 1 AND LOWER(q.title) LIKE ? AND q.is_public = 1 " +
                    "ORDER BY q.created_at DESC " +
                    "LIMIT 24";

        return jdbcTemplate.query(sql,
            ps -> {
                ps.setString(1, lowerCaseAuthor);
                ps.setString(2, lowerCaseKeyword);
            },
            (rs, rowNum) -> new QuizInfoDto(
                rs.getLong("quiz_id"),
                rs.getString("quiz_title"),
                rs.getLong("category_id"),
                rs.getString("author"),
                rs.getString("thumbnail_filepath")
            )
        );
    }
    @Override
    public List<QuizInfoDto> findQuizzesByAuthor(String author) {
        String lowerCaseAuthor = author.toLowerCase();
    
        String sql = "SELECT q.quiz_id, q.title AS quiz_title, q.category_id, u.username AS author, m.file_path AS thumbnail_filepath " +
                     "FROM quiz q " +
                     "JOIN multi_media m ON q.media_id = m.media_id " +
                     "JOIN collaborator c ON q.quiz_id = c.quiz_id " +
                     "JOIN user u ON c.user_id = u.user_id " +
                     "WHERE LOWER(u.username) LIKE CONCAT('%', LOWER(?), '%') AND c.type_id = 1 AND q.is_public = 1 " +
                     "ORDER BY q.created_at DESC " +
                     "LIMIT 24";
    
        return jdbcTemplate.query(sql,
            ps -> {
                ps.setString(1, lowerCaseAuthor);
            },
            (rs, rowNum) -> new QuizInfoDto(
                rs.getLong("quiz_id"),
                rs.getString("quiz_title"),
                rs.getLong("category_id"),
                rs.getString("author"),
                rs.getString("thumbnail_filepath")
            )
        );
    }

    @Override
    public Boolean deleteQuizById(Long quizId) {
        String sql = "DELETE FROM quiz WHERE quiz_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, quizId);
        return rowsAffected > 0;
    }
}