package no.ntnu.idi.idatt2105.quizopia.backend.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.AnswerDto;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuestionDto;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizDto;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizInfoDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Answer;
import no.ntnu.idi.idatt2105.quizopia.backend.model.AnswerQuestion;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Collaborator;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Question;
import no.ntnu.idi.idatt2105.quizopia.backend.model.QuizQuestion;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuizRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.AnswerQuestionRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.AnswerRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.CollaboratorRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuestionRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.QuizQuestionRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.UserRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.CategoryRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
    private final QuestionRepository questionRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final QuizMapper quizMapper;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;
    private final AnswerRepository answerRepository;
    private final AnswerQuestionRepository answerQuestionRepository;
    private final UserRepository userRepository;
    private final QuizDtoMapper quizDtoMapper;
    private final QuestionDtoMapper questionDtoMapper;
    private final CategoryRepository categoryRepository;

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
            Collaborator collaborator = new Collaborator();
            collaborator.setQuizId(quizSaved.getQuizId());
            collaborator.setUserId(quizDto.getuserId());
            collaborator.setTypeId(1L); // Author
            collaboratorRepository.save(collaborator);
            log.debug("Saved user to collaborator with ID: {}", quizDto.getuserId());

            quizDto.getQuestions().forEach(questionDto -> {
                try {
                    // Map QuestionDto to Question object
                    Question questionDetails = questionMapper.toQuestion(questionDto);

                    // Store the Question to the Database (if it is new)
                    Question questionSaved = questionRepository.save(questionDetails);
                    log.debug("Saved question with ID: {}", questionSaved.getQuestionId());

                    questionDto.getAnswers().forEach(answerDto ->  {
                        
                        // Map AnswerDto to Answer object
                        Answer answerDetails = answerMapper.toAnswer(answerDto);

                        // Store the Answer to the Database (if it is new)
                        Answer answerSaved = answerRepository.save(answerDetails);
                        log.debug("Saved answer with ID: {}", answerSaved.getAnswerText());

                        // Store the connection between the Answer and the Question
                        AnswerQuestion answerQuestion = new AnswerQuestion();
                        answerQuestion.setQuestionId(questionSaved.getQuestionId());
                        answerQuestion.setAnswerId(answerSaved.getAnswerId());
                        answerQuestion.setCorrect(answerDto.getIsCorrect());
                        answerQuestionRepository.save(answerQuestion);
                    });

                    // Store the connection between the Question and the Quiz
                    QuizQuestion quizQuestion = new QuizQuestion();
                    quizQuestion.setQuizId(quizSaved.getQuizId());
                    quizQuestion.setQuestionId(questionSaved.getQuestionId());
                    quizQuestionRepository.save(quizQuestion);
                } catch (Exception e) {
                    log.error("Error storing Question and Answer: {}", e.getMessage(), e);
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
     * Creates a new quiz in the database based on the provided QuizDto.
     *
     * @param quizDto the quiz data transfer object containing quiz creation details.
     * @return the created Quiz entity.
     */
    @Transactional
    public Quiz updateQuiz(QuizDto quizDto) {
        log.info("Updating existing quiz with ID: {}", quizDto.getquizId());
        try {
            // Map QuizDto to Quiz object and update it
            Quiz quizDetails = quizMapper.toQuiz(quizDto);
            quizDetails.setQuizId(quizDto.getquizId());
            Quiz quizSaved = quizRepository.update(quizDetails);
            log.info("Updated quiz with ID: {}", quizSaved.getQuizId());

            // Handle questions
            List<Long> currentQuestionIds = quizDto.getQuestions().stream().map(questionDto -> {
                Question question = handleQuestion(questionDto, quizSaved);
                return question.getQuestionId();
            }).collect(Collectors.toList());

            // Delete questions removed from the quiz
            cleanUpQuestions(quizDetails.getQuizId(), currentQuestionIds);

            return quizDetails;
        } catch (Exception e) {
            log.error("Error updating quiz: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update quiz", e);
        }
    }

    /**
     * Processes a single question from the QuizDto, 
     * handling the creation or update of the question and its answers.
     *
     * @param questionDto The DTO containing the question details.
     * @param quizSaved   The Quiz entity to which the question belongs.
     * @return The Question entity after being saved or updated in the database.
     */
    private Question handleQuestion(QuestionDto questionDto, Quiz quizSaved) {
        boolean isNewQuestion = questionDto.getquestionId() == null;
        Question question = questionMapper.toQuestion(questionDto);
        question.setQuestionId(questionDto.getquestionId());
        
        if (isNewQuestion) {
            question = questionRepository.save(question);
            log.info("Saved new question with ID: {}", question.getQuestionId());
            
            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setQuizId(quizSaved.getQuizId());
            quizQuestion.setQuestionId(question.getQuestionId());
            quizQuestionRepository.save(quizQuestion);
        }
        
        final Question finalQuestion = question;

        List<Long> currentAnswerIds = questionDto.getAnswers().stream().map(answerDto -> {
            Answer answer = handleAnswer(answerDto, finalQuestion, isNewQuestion);
            return answer.getAnswerId();
        }).collect(Collectors.toList());

        cleanUpAnswers(question.getQuestionId(), currentAnswerIds);
        
        return question;
    }

    /**
     * Handles the creation or update of an answer associated with a particular question. 
     *
     * @param answerDto      The DTO containing the answer details.
     * @param question       The Question entity to which the answer belongs.
     * @param isNewQuestion  Indicates whether the question is new.
     * @return The Answer entity after being saved or updated in the database.
     */
    private Answer handleAnswer(AnswerDto answerDto, Question question, boolean isNewQuestion) {
        Answer answer;
        boolean isNewAnswer = answerDto.getanswerId() == null;
        
        if (isNewAnswer) {
            answer = answerMapper.toAnswer(answerDto);
            answer = answerRepository.save(answer);
            log.info("Saved new answer with ID: {}", answer.getAnswerId());
        } else {
            answer = answerMapper.toAnswer(answerDto);
            answer.setAnswerId(answerDto.getanswerId());
        }
    
        if (isNewQuestion || isNewAnswer) {
            AnswerQuestion answerQuestion = new AnswerQuestion();
            answerQuestion.setQuestionId(question.getQuestionId());
            answerQuestion.setAnswerId(answer.getAnswerId());
            answerQuestion.setCorrect(answerDto.getIsCorrect());
            answerQuestionRepository.save(answerQuestion);
            log.info("Connected answer with ID: {} to question with ID {}", answerQuestion.getAnswerId(), answerQuestion.getQuestionId());
        }

        return answer;
    }
    


    /**
     * Cleans up the questions associated with a quiz by 
     * removing any questions that are no longer part of the updated quiz.
     *
     * @param quizId             The ID of the quiz.
     * @param currentQuestionIds The IDs of questions that should remain.
     */
    private void cleanUpQuestions(Long quizId, List<Long> currentQuestionIds) {
        List<Long> oldQuestionIds = quizQuestionRepository.getQuestionIdByQuiz(quizId);
        oldQuestionIds.forEach(id -> {
            if (!currentQuestionIds.contains(id)) {
                log.info("Removing question with ID: {} from quiz", id);
                quizQuestionRepository.delete(quizId, id);
            }
        });
    }

    /**
     * Cleans up the answers associated with a question by 
     * removing any answers that are no longer part of the updated question.
     *
     * @param questionId       The ID of the question.
     * @param currentAnswerIds The IDs of answers that should remain.
     */
    private void cleanUpAnswers(Long questionId, List<Long> currentAnswerIds) {
        List<Long> oldAnswerIds = answerQuestionRepository.getAnswerIdByQuestionId(questionId);
        oldAnswerIds.forEach(id -> {
            if (!currentAnswerIds.contains(id)) {
                log.info("Removing answer with ID: {} from question: {}", id, questionId);
                answerQuestionRepository.delete(questionId, id);
            }
        });
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
            .map(userId -> {
                List<QuizInfoDto> quizzes = quizRepository.findQuizzesByCreatorId(userId);
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
                .map(quiz -> new QuizInfoDto(quiz.getquizId(), quiz.getQuiz_title(), quiz.getmediaId(), quiz.getThumbnail_filepath()))
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
                .map(quiz -> new QuizInfoDto(quiz.getquizId(), quiz.getQuiz_title(), quiz.getmediaId(), quiz.getThumbnail_filepath()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a quiz by its ID, including all its question and their respective answer.
     *
     * @param quizId The ID of the quiz to retrieve.
     * @return QuizDto containing the quiz details, or null if not found.
     */
    public QuizDto getQuizById(Long quizId) {
        log.info("Fetching quiz with ID: {}", quizId);
        Quiz quiz = quizRepository.findQuizById(quizId);
        if (quiz == null) {
            log.info("Quiz not found with ID: {}", quizId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
        }
        QuizDto quizDto = quizDtoMapper.toQuizDto(quiz);
        quizDto.setuserId(collaboratorRepository.findAutherByQuizId(quizId).get());

        List<Question> questions = questionRepository.findQuestionByQuizId(quizId);
        if (questions.isEmpty()) {
            log.info("No question found for quiz ID: {}", quizId);
        } else {
            List<QuestionDto> questionDtos = questions.stream()
                    .map(question -> {
                        QuestionDto questionDto = questionDtoMapper.toQuestionDto(question);
                        List<AnswerDto> answerDto = answerRepository.findAnswerByQuestionId(question.getQuestionId());
                        questionDto.setAnswers(answerDto);
                        return questionDto;
                    })
                    .collect(Collectors.toList());

            quizDto.setQuestions(questionDtos);
        }
        return quizDto;
    }

    public String getCategoryById(Long categoryId) {
        String category = categoryRepository.getCategoryById(categoryId);
        return category;
    }

}
