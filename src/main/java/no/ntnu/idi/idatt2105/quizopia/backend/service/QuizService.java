package no.ntnu.idi.idatt2105.quizopia.backend.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Collaborators;
import no.ntnu.idi.idatt2105.quizopia.backend.model.QuizQuestions;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuizRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.CollaboratorsRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuestionsRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuizQuestionsRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizQuestionsRepository quizQuestionsRepository;
    private final CollaboratorsRepository collaboratorsRepository;
    private final QuizMapper quizMapper;

    @Transactional
    public Quiz createQuiz(QuizDto quizDto) {
        // Map QuizDto object to Quiz object
        Quiz quizDetails = quizMapper.toQuiz(quizDto);

        // Store the Quiz to the Database
        Quiz quizSaved = quizRepository.save(quizDetails);

        // Store the connection between the User (author) and the Quiz
        Collaborators collaborators = new Collaborators();
        collaborators.setQuizId(quizSaved.getQuizId());
        collaborators.setUserId(quizDto.getUser_id());
        collaborators.setTypeId(1L); // Author
        collaboratorsRepository.save(collaborators);

        // Store the connection between all the Questions and the Quiz
        if(!quizDto.getQuestions().isEmpty()) {
            quizDto.getQuestions().forEach(question_id -> {
                try {
                    QuizQuestions quizQuestions = new QuizQuestions();
                    quizQuestions.setQuizId(quizSaved.getQuizId());
                    quizQuestions.setQuestionId(question_id);
                    quizQuestionsRepository.save(quizQuestions);
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                }
            });
        }
        return quizDetails;
    }
}
