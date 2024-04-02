package no.ntnu.idi.idatt2105.quizopia.backend.repository;

import no.ntnu.idi.idatt2105.quizopia.backend.model.Collaborator;

public interface CollaboratorRepository {
    int save(Collaborator collaborator);
}