package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.quiz;

import no.ntnu.idi.idatt2105.quizopia.backend.model.Collaborator;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz.CollaboratorRepository;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCollaboratorRepository implements CollaboratorRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCollaboratorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Collaborator collaborator) {
        String sql = "INSERT INTO collaborator (user_id, quiz_id, type_id) VALUES (?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            collaborator.getUserId(), collaborator.getQuizId(), collaborator.getTypeId());
    }

    public Optional<Long> findAutherByQuizId(Long quizId) {
        String sql = "SELECT user_id FROM collaborator WHERE quiz_id = ?";
        try {
            Long id = jdbcTemplate.queryForObject(sql, Long.class, quizId);
            return Optional.ofNullable(id); 
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public int deleteQuizById(Long quizId) {
        String sql = "DELETE FROM collaborator WHERE quiz_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, quizId);
        return rowsAffected;
    }

    @Override
    public int deleteUserById(Long userId) {
        String sql = "DELETE FROM collaborator WHERE user_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, userId);
        return rowsAffected;
    }
}