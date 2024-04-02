package no.ntnu.idi.idatt2105.quizopia.backend.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.AnswersDto;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuestionsDto;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizDto;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizInfoDto;
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
import no.ntnu.idi.idatt2105.quizopia.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service for handling quiz operations.
 */
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
    private final UserRepository userRepository;
    private final QuizDtoMapper quizDtoMapper;
    private final QuestionDtoMapper questionDtoMapper;

    /**
     * Creates a new quiz in the database based on the provided QuizDto.
     *
     * @param quizDto the quiz data transfer object containing quiz creation details.
     * @return the created Quiz entity.
     */
    @Transactional
    public Quiz createQuiz(QuizDto quizDto) {
        log.info("Creating new quiz with title: {}", quizDto.getTitle());
        try {
            // Map QuizDto to Quiz object
            Quiz quizDetails = quizMapper.toQuiz(quizDto);

            // Store the Quiz to the Database
            Quiz quizSaved = quizRepository.save(quizDetails);
            log.debug("Saved quiz with ID: {}", quizSaved.getQuizId());

            // Store the connection between the User (author) and the Quiz
            Collaborators collaborators = new Collaborators();
            collaborators.setQuizId(quizSaved.getQuizId());
            collaborators.setUserId(quizDto.getUser_id());
            collaborators.setTypeId(1L); // Author
            collaboratorsRepository.save(collaborators);
            log.debug("Saved user to collaborator with ID: {}", quizDto.getUser_id());

            quizDto.getQuestions().forEach(questionsDto -> {
                try {
                    // Map QuestionsDto to Questions object
                    Questions questionsDetails = questionMapper.toQuestion(questionsDto);

                    // Store the Questions to the Database (if it is new)
                    Questions questionSaved = questionsRepository.save(questionsDetails);
                    log.debug("Saved question with ID: {}", questionSaved.getQuestionId());

                    questionsDto.getAnswers().forEach(answersDto ->  {
                        
                        // Map AnswersDto to Answers object
                        Answers answersDetails = answersMapper.toAnswers(answersDto);

                        // Store the Answers to the Database (if it is new)
                        Answers answersSaved = answersRepository.save(answersDetails);
                        log.debug("Saved answer with ID: {}", answersSaved.getAnswerText());

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
                    log.error("Error storing Questions and Answers: {}", e.getMessage(), e);
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                }
            });
            return quizDetails;
        } catch (Exception e) {
            log.error("Error creating quiz: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create quiz", e);
        }
    }

    /**
     * Finds all quizzes created by a specific user.
     *
     * @param username the username of the user whose quizzes are to be found.
     * @return a list of QuizInfoDto representing the user's quizzes.
     */
    public List<QuizInfoDto> findQuizzesCreatedByUserId(String username) {
        log.info("Fetching quizzes created by user: {}", username);
        return userRepository.findIdByName(username)
            .map(user_id -> {
                List<QuizInfoDto> quizzes = quizRepository.findQuizzesByCreatorId(user_id);
                log.info("Found {} quizzes created by user: {}", quizzes.size(), username);
                return quizzes;
            })
            .orElseGet(() -> {
                log.info("No quizzes found for user: {}", username);
                return Collections.emptyList();
            });
    }

    /**
     * Finds all public quizzes.
     *
     * @return a list of QuizInfoDto representing all public quizzes.
     */
    public List<QuizInfoDto> findPublicQuizzes() {
        log.info("Fetching all public quizzes");
        List<QuizInfoDto> publicQuizzes = quizRepository.findPublicQuizzes();
        if (publicQuizzes == null || publicQuizzes.isEmpty()) {
            log.info("No public quizzes found");
            return Collections.emptyList();
        }
        return publicQuizzes.stream()
                .map(quiz -> new QuizInfoDto(quiz.getQuiz_id(), quiz.getQuiz_title(), quiz.getMedia_id(), quiz.getThumbnail_filepath()))
                .collect(Collectors.toList());
    }

    /**
     * Finds quizzes by category.
     *
     * @param category The category name to filter quizzes.
     * @return a list of QuizInfoDto representing quizzes in the specified category.
     */
    public List<QuizInfoDto> findQuizzesByCategory(String category) {
        log.info("Fetching quizzes in category: {}", category);
        List<QuizInfoDto> quizzesByCategory = quizRepository.findQuizzesByCategoryName(category);
        if (quizzesByCategory == null || quizzesByCategory.isEmpty()) {
            log.info("No quizzes found in category: {}", category);
            return Collections.emptyList();
        }
        return quizzesByCategory.stream()
                .map(quiz -> new QuizInfoDto(quiz.getQuiz_id(), quiz.getQuiz_title(), quiz.getMedia_id(), quiz.getThumbnail_filepath()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a quiz by its ID, including all its questions and their respective answers.
     *
     * @param quiz_id The ID of the quiz to retrieve.
     * @return QuizDto containing the quiz details, or null if not found.
     */
    public QuizDto getQuizById(Long quiz_id) {
        log.info("Fetching quiz with ID: {}", quiz_id);
        Quiz quiz = quizRepository.findQuizById(quiz_id);
        if (quiz == null) {
            log.info("Quiz not found with ID: {}", quiz_id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
        }
        QuizDto quizDto = quizDtoMapper.toQuizDto(quiz);

        List<Questions> questions = questionsRepository.findQuestionsByQuizId(quiz_id);
        if (questions.isEmpty()) {
            log.info("No questions found for quiz ID: {}", quiz_id);
        } else {
            List<QuestionsDto> questionsDtos = questions.stream()
                    .map(question -> {
                        QuestionsDto questionsDto = questionDtoMapper.toQuestionDto(question);
                        List<AnswersDto> answersDto = answersRepository.findAnswersByQuestionId(question.getQuestionId());
                        questionsDto.setAnswers(answersDto);
                        return questionsDto;
                    })
                    .collect(Collectors.toList());

            quizDto.setQuestions(questionsDtos);
        }
        return quizDto;
    }
}
