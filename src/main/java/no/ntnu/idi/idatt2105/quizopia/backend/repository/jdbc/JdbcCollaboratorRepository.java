package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import no.ntnu.idi.idatt2105.quizopia.backend.model.Collaborator;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.CollaboratorRepository;

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
}