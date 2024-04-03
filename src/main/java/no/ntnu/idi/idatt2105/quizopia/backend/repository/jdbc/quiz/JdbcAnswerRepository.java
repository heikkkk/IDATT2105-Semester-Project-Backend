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

    @Override
    public Answer save(Answer answer) {
        String sql = "INSERT INTO answer (answer_text) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, answer.getAnswerText());
            return ps;
        }, keyHolder);
        
        // Retrieve the generated primary key
        @SuppressWarnings("null")
        long generatedId = keyHolder.getKey().longValue();
        
        // Update the quiz object with the generated primary key
        answer.setAnswerId(generatedId);
        
        return answer;
    }

    @Override
    public List<AnswerDto> findAnswerByQuestionId(Long question_id) {
        String sql = "SELECT a.*, aq.is_correct " +
                     "FROM answer a " + 
                     "JOIN answer_question aq ON a.answer_id = aq.answer_id " +
                     "WHERE aq.question_id = ? ";
        return jdbcTemplate.query(sql, new Object[]{question_id}, (rs, rowNum) -> new AnswerDto(
            rs.getLong("answer_id"),
            rs.getString("answer_text"),
            rs.getBoolean("aq.is_correct")
        ));             
    }
}
