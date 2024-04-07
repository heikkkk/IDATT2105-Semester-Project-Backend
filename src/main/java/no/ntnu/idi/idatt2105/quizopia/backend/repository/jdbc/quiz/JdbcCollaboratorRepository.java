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

    /**
     * Saves a Collaborator to the database.
     *
     * @param collaborator the Collaborator to be saved.
     * @return the number of rows affected.
     */
    @Override
    public int save(Collaborator collaborator) {
        String sql = "INSERT INTO collaborator (user_id, quiz_id, type_id) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, collaborator.getUserId(), collaborator.getQuizId(), collaborator.getTypeId());
    }

    /**
     * Finds the user ID of the author who made the given quiz with the ID.
     *
     * @param quizId the ID of the quiz.
     * @return an Optional containing the user ID or empty.
     */
    public Optional<Long> findAutherByQuizId(Long quizId) {
        String sql = "SELECT user_id FROM collaborator WHERE quiz_id = ?";
        try {
            Long id = jdbcTemplate.queryForObject(sql, Long.class, quizId);
            return Optional.ofNullable(id); 
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Deletes all collaborator entries associated with a specific quiz ID.
     *
     * @param quizId the ID of the quiz to delete collaborators for.
     * @return the number of rows affected.
     */
    @Override
    public int deleteQuizById(Long quizId) {
        String sql = "DELETE FROM collaborator WHERE quiz_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, quizId);
        return rowsAffected;
    }

    /**
     * Deletes all collaborator entries associated with a specific user ID.
     *
     * @param userId the ID of the user to delete collaborators for.
     * @return the number of rows affected.
     */
    @Override
    public int deleteUserById(Long userId) {
        String sql = "DELETE FROM collaborator WHERE user_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, userId);
        return rowsAffected;
    }
}