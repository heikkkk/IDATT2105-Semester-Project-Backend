package no.ntnu.idi.idatt2105.quizopia.backend.repository;

import java.util.Optional;

import no.ntnu.idi.idatt2105.quizopia.backend.model.Collaborator;

public interface CollaboratorRepository {
    int save(Collaborator collaborator);

    Optional<Long> findAutherByQuizId(Long quiz_id);
}