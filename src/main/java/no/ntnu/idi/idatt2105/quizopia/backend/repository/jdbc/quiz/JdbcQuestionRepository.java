package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.quiz;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.sql.Types;

import no.ntnu.idi.idatt2105.quizopia.backend.model.Question;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz.QuestionRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcQuestionRepository implements QuestionRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcQuestionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

     /**
     * Saves a Question to the database.
     *
     * @param question the Question to be saved.
     * @return the saved Question.
     */
    @Override
    public Question save(Question question) {
        String sql = "INSERT INTO question (question_name, question_text, explanations, question_duration, is_public, type_id, difficulty_id, media_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, question.getQuestionName());
            ps.setString(2, question.getQuestionText());
            ps.setString(3, question.getExplanations());
            ps.setInt(4, question.getQuestionDuration());
            ps.setBoolean(5, question.getIsPublic());
            ps.setObject(6, question.getTypeId(), Types.BIGINT); 
            ps.setObject(7, question.getDifficultyId(), Types.BIGINT);
            ps.setObject(8, question.getMediaId(), Types.BIGINT);
            return ps;
        }, keyHolder);
        
        long generatedId = keyHolder.getKey().longValue();
        question.setQuestionId(generatedId);
        
        return question;
    }

    /**
     * Finds and returns all questions associated with a given quiz ID.
     *
     * @param quizId the ID of the quiz.
     * @return a list of Questions associated with the quiz ID.
     */
    @Override
    public List<Question> findQuestionByQuizId(Long quizId) {
        String sql = "SELECT q.* " +
                    "FROM question q " +
                    "JOIN quiz_question qq ON q.question_id = qq.question_id " +
                    "WHERE qq.quiz_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Question question = new Question();
                question.setQuestionId(rs.getLong("question_id"));
                question.setQuestionName(rs.getString("question_name"));
                question.setQuestionText(rs.getString("question_text"));
                question.setExplanations(rs.getString("explanations"));
                question.setQuestionDuration(rs.getInt("question_duration")); 
                question.setIsPublic(rs.getBoolean("is_public"));
                question.setTypeId(rs.getLong("type_id"));
                question.setDifficultyId(rs.getLong("difficulty_id"));
                question.setMediaId(rs.getLong("media_id"));
                return question;
            }, quizId); 
    }

}