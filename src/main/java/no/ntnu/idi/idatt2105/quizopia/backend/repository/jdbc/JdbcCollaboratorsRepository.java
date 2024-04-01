package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import no.ntnu.idi.idatt2105.quizopia.backend.model.Collaborators;
import no.ntnu.idi.idatt2105.quizopia.backend.model.QuizQuestions;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.CollaboratorsRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuizQuestionsRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCollaboratorsRepository implements CollaboratorsRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCollaboratorsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Collaborators collaborators) {
        String sql = "INSERT INTO Collaborators (user_id, quiz_id, type_id) VALUES (?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            collaborators.getUserId(), collaborators.getQuizId(), collaborators.getTypeId());
    }
}