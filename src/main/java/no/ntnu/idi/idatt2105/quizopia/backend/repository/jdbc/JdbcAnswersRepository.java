package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import no.ntnu.idi.idatt2105.quizopia.backend.dto.AnswersDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Answers;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.AnswersRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAnswersRepository implements AnswersRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAnswersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Answers save(Answers answers) {
        String sql = "INSERT INTO answers (answer_text) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, answers.getAnswerText());
            return ps;
        }, keyHolder);
        
        // Retrieve the generated primary key
        @SuppressWarnings("null")
        long generatedId = keyHolder.getKey().longValue();
        
        // Update the quiz object with the generated primary key
        answers.setAnswerId(generatedId);
        
        return answers;
    }

    @Override
    public List<AnswersDto> findAnswersByQuestionId(Long questions_id) {
        String sql = "SELECT a.*, aq.is_correct " +
                     "FROM answers a " + 
                     "JOIN answers_questions aq ON a.answer_id = aq.answer_id " +
                     "WHERE aq.question_id = ? ";
        return jdbcTemplate.query(sql, new Object[]{questions_id}, (rs, rowNum) -> new AnswersDto(
            rs.getLong("answer_id"),
            rs.getString("answer_text"),
            rs.getBoolean("aq.is_correct")
        ));             
    }
}
