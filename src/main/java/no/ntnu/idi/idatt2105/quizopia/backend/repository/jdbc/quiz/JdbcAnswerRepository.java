package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.quiz;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import no.ntnu.idi.idatt2105.quizopia.backend.dto.AnswerDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Answer;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz.AnswerRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAnswerRepository implements AnswerRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAnswerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Saves an Answer entity to the database and then return the 
     * saved.
     * 
     * @param answer the Answer entity you wanna save.
     * @return the saved Answer entity.
     */
    @Override
    public Answer save(Answer answer) {
        String sql = "INSERT INTO answer (answer_text) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, answer.getAnswerText());
            return ps;
        }, keyHolder);
        
        long generatedId = keyHolder.getKey().longValue();
        
        answer.setAnswerId(generatedId);
        
        return answer;
    }

    /**
     * Finds answers mapped to a specific question ID 
     * and then adds them to list as AnswerDto objects.
     * 
     * @param questionId the ID of the question
     * @return a list of AnswerDto.
     */
    @Override
    public List<AnswerDto> findAnswerByQuestionId(Long questionId) {
        String sql = "SELECT a.*, aq.is_correct " +
                     "FROM answer a " + 
                     "JOIN answer_question aq ON a.answer_id = aq.answer_id " +
                     "WHERE aq.question_id = ? ";
        return jdbcTemplate.query(sql, 
        ps -> ps.setLong(1, questionId), 
        (rs, rowNum) -> new AnswerDto(
            rs.getLong("answer_id"),
            rs.getString("answer_text"),
            rs.getBoolean("is_correct") 
        ));             
    }
}
