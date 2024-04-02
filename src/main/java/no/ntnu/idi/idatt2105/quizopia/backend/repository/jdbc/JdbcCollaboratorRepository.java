package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import no.ntnu.idi.idatt2105.quizopia.backend.model.Collaborator;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.CollaboratorRepository;

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

    public Optional<Long> findAutherByQuizId(Long quiz_id) {
        String sql = "SELECT user_id FROM collaborator WHERE quiz_id = ?";
        try {
            Long id = jdbcTemplate.queryForObject(
                sql,
                new Object[]{quiz_id},
                (rs, rowNum) -> rs.getLong("user_id")
            );
            return Optional.ofNullable(id); // Changed to ofNullable in case id is null
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}