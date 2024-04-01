package no.ntnu.idi.idatt2105.quizopia.backend.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Collaborators;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Questions;
import no.ntnu.idi.idatt2105.quizopia.backend.model.QuizQuestions;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuizRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.CollaboratorsRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuestionsRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuizQuestionsRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionsRepository questionRepository;
    private final QuizQuestionsRepository quizQuestionsRepository;
    private final CollaboratorsRepository collaboratorsRepository;
    private final QuizMapper quizMapper;
    private final QuestionMapper questionMapper;

    @Transactional
    public Quiz createQuiz(QuizDto quizDto) {
        Quiz quizDetails = quizMapper.toQuiz(quizDto);

        quizRepository.save(quizDetails);
        Optional<Quiz> existingQuiz = quizRepository.findQuizByAttributes(quizDetails);

        Collaborators collaborators = new Collaborators();
        collaborators.setQuizId(existingQuiz.get().getQuizId());
        collaborators.setUserId(quizDto.getUser_id());
        collaborators.setTypeId(1L);
        collaboratorsRepository.save(collaborators);

        quizDto.getQuestions().forEach(questionsDto -> {
            Questions questionsDetails = questionMapper.toQuestion(questionsDto); 

            try {
                Optional<Questions> existingQuestion = questionRepository.findQuestionByAttributes(questionsDetails);
                if (existingQuestion.isPresent()) {
                    questionsDetails = existingQuestion.get();
                } else {
                    questionRepository.save(questionsDetails);
                    questionsDetails = existingQuestion.get();
                }

                // Link the (new or existing) question to the quiz
                QuizQuestions quizQuestions = new QuizQuestions();
                quizQuestions.setQuizId(existingQuiz.get().getQuizId());
                quizQuestions.setQuestionId(questionsDetails.getQuestionId());
                quizQuestionsRepository.save(quizQuestions);
                
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        });
        return quizDetails;
    }
}
