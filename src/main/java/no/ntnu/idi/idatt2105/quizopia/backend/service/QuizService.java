package no.ntnu.idi.idatt2105.quizopia.backend.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Answers;
import no.ntnu.idi.idatt2105.quizopia.backend.model.AnswersQuestions;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Collaborators;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Questions;
import no.ntnu.idi.idatt2105.quizopia.backend.model.QuizQuestions;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuizRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.AnswersQuestionsRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.AnswersRepository;
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
    private final QuestionsRepository questionsRepository;
    private final QuizQuestionsRepository quizQuestionsRepository;
    private final CollaboratorsRepository collaboratorsRepository;
    private final QuizMapper quizMapper;
    private final QuestionMapper questionMapper;
    private final AnswersMapper answersMapper;
    private final AnswersRepository answersRepository;
    private final AnswersQuestionsRepository answersQuestionsRepository;

    @Transactional
    public Quiz createQuiz(QuizDto quizDto) {
        // Map QuizDto to Quiz object
        Quiz quizDetails = quizMapper.toQuiz(quizDto);

        // Store the Quiz to the Database
        Quiz quizSaved = quizRepository.save(quizDetails);

        // Store the connection between the User (author) and the Quiz
        Collaborators collaborators = new Collaborators();
        collaborators.setQuizId(quizSaved.getQuizId());
        collaborators.setUserId(quizDto.getUser_id());
        collaborators.setTypeId(1L); // Author
        collaboratorsRepository.save(collaborators);

        quizDto.getQuestions().forEach(questionsDto -> {
            try {
                // Map QuestionsDto to Questions object
                Questions questionsDetails = questionMapper.toQuestion(questionsDto);

                // Store the Questions to the Database (if it is new)
                Questions questionSaved = questionsRepository.save(questionsDetails);

                questionsDto.getAnswers().forEach(answersDto ->  {
                    
                    // Map AnswersDto to Answers object
                    Answers answersDetails = answersMapper.toAnswers(answersDto);

                    // Store the Answers to the Database (if it is new)
                    Answers answersSaved = answersRepository.save(answersDetails);

                    // Store the connection between the Answer and the Question
                    AnswersQuestions answersQuestions = new AnswersQuestions();
                    answersQuestions.setQuestionId(questionSaved.getQuestionId());
                    answersQuestions.setAnswerId(answersSaved.getAnswerId());
                    answersQuestions.setCorrect(answersDto.getIsCorrect());
                    answersQuestionsRepository.save(answersQuestions);
                });

                // Store the connection between the Question and the Quiz
                QuizQuestions quizQuestions = new QuizQuestions();
                quizQuestions.setQuizId(quizSaved.getQuizId());
                quizQuestions.setQuestionId(questionSaved.getQuestionId());
                quizQuestionsRepository.save(quizQuestions);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        });
        return quizDetails;
    }
}
