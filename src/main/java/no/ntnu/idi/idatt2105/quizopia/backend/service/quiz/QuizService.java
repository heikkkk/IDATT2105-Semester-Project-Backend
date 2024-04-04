package no.ntnu.idi.idatt2105.quizopia.backend.service.quiz;

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
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz.AnswerQuestionRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz.AnswerRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz.CategoryRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz.CollaboratorRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz.QuestionRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz.QuizQuestionRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz.QuizRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.user.UserRepository;

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
     * Create a new quiz object, question objects and answer objects in the database.
     * The relations between these entities are also configured.
     * 
     * TODO: Handle the case when existing questions or answers are sent from the client to avoid duplicates.
     *
     * @param quizDto date transfer object containing all the information on the quiz.
     * @return the created Quiz.
     */
    @Transactional
    public Quiz createQuiz(QuizDto quizDto) {
        log.info("Creating new quiz with title: {}", quizDto.getTitle());
        try {
            Quiz quizDetails = quizMapper.toQuiz(quizDto);

            // Store the Quiz to the Database
            Quiz quizSaved = quizRepository.save(quizDetails);
            log.info("Saved quiz with ID: {}", quizSaved.getQuizId());

            // Store the relations between the User (author) and the Quiz (Collaborator)
            Collaborator collaborator = new Collaborator();
            collaborator.setQuizId(quizSaved.getQuizId());
            collaborator.setUserId(quizDto.getuserId());
            collaborator.setTypeId(1L); // Author
            collaboratorRepository.save(collaborator);
            log.info("Saved user to collaborator with ID: {}", quizDto.getuserId());

            quizDto.getQuestions().forEach(questionDto -> {
                try {
                    Question questionDetails = questionMapper.toQuestion(questionDto);

                    // Store the Question to the Database
                    Question questionSaved = questionRepository.save(questionDetails);
                    log.debug("Saved question with ID: {}", questionSaved.getQuestionId());

                    // Store the relations between the Question and the Quiz (QuizQuestion)
                    QuizQuestion quizQuestion = new QuizQuestion();
                    quizQuestion.setQuizId(quizSaved.getQuizId());
                    quizQuestion.setQuestionId(questionSaved.getQuestionId());
                    quizQuestionRepository.save(quizQuestion);

                    questionDto.getAnswers().forEach(answerDto ->  {
                        Answer answerDetails = answerMapper.toAnswer(answerDto);

                        // Store the Answer to the Database 
                        Answer answerSaved = answerRepository.save(answerDetails);
                        log.debug("Saved answer with ID: {}", answerSaved.getAnswerText());

                        // Store the relations between the Answer and the Question (AnswerQuestion)
                        AnswerQuestion answerQuestion = new AnswerQuestion();
                        answerQuestion.setQuestionId(questionSaved.getQuestionId());
                        answerQuestion.setAnswerId(answerSaved.getAnswerId());
                        answerQuestion.setCorrect(answerDto.getIsCorrect());
                        answerQuestionRepository.save(answerQuestion);
                    });
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
     * Update an existing quiz.
     * The entire quiz entity stored in the database will be replaced (based on quizId).
     * 
     * The relation between the quiz and question entities will be configured in the following way:
     * - If a question entity is sent with a question_id (not null) then it will be ignored.
     * - If a question entity is sent with question_id less than or equal to 0 then it will be 
     * added to the database (question) and the relation between the quiz and question (quiz_question)
     * - If a question entity that is currently stored in the database, is not sent with the dto, 
     * then the relation between the quiz and that question will be removed (quiz_question).
     * 
     * The relation between the question and answer entities will be configured in the following way:
     * - If an answer entity is sent with an answer_id (not null) then it will be ignored.
     * - If an answer entity is sent with answer_id less than or equal to 0 then it will be added to the database (answer)
     * and the relation between the answer and question (answer_question)
     * - If an answer entity that is currently stored in the database, is not sent with the dto,
     * then the relation between the question and that answer will be removed (answer_question).
     * 
     * NOTE: Question and Answer entities will not be deleted.
     * 
     * @param quizDto date transfer object containing all the information on the quiz.
     * @return the updated Quiz.
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
     * Processes a question entity.
     * Handle the creation/updating the question and its answer entities. 
     *
     * @param questionDto The data transfer object for the question.
     * @param quizSaved   The quiz the question belongs to.
     * @return The question.
     */
    private Question handleQuestion(QuestionDto questionDto, Quiz quizSaved) {
        boolean isNewQuestion = questionDto.getquestionId() <= 0;
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
     * Processes an answer entity.
     * Handle the creation/updating the answer entities. 
     *
     * @param AnswerDto The data transfer object for the answer.
     * @param question  The question the answer belongs to.
     * @param isNewQuestion flag for if the question it is being added to is new or already exists.
     * @return The answer.
     */
    private Answer handleAnswer(AnswerDto answerDto, Question question, boolean isNewQuestion) {
        Answer answer;
        boolean isNewAnswer = answerDto.getanswerId() <= 0;
        
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
     * Remove the questions associated with a quiz
     * that are no longer part of the quiz. 
     *
     * @param quizId The ID of the quiz.
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
     * Remove the answers associated with the question
     * that are no longer part of the question.
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
     * Find all public quizzes. Limited to 24 quizzes maximum.
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
     * Finds quizzes with titles that match keyword. 
     * Limited to 24 quizzes maximum.
     *
     * @param keyword The keyword to filter quizzes.
     * @return a list of QuizInfoDto representing quizzes with titles that match keyword.
     */
    public List<QuizInfoDto> findQuizzesByKeyword(String keyword) {
        log.info("Fetching quizzes with title that match keyword: {}", keyword);
        List<QuizInfoDto> quizzesByKeyword = quizRepository.findQuizzesByKeyword(keyword);
        if (quizzesByKeyword == null || quizzesByKeyword.isEmpty()) {
            log.info("No quizzes found with titles that match keyword: {}", keyword);
            return Collections.emptyList();
        }
        return quizzesByKeyword.stream()
                .map(quiz -> new QuizInfoDto(quiz.getquizId(), quiz.getQuiz_title(), quiz.getmediaId(), quiz.getThumbnail_filepath()))
                .collect(Collectors.toList());
    }

    /**
     * Finds quizzes with titles that match keyword and matching category.
     * Limited to 24 quizzes maximum.
     *
     * @param keyword The keyword to filter quizzes.
     * @param category The category to filter quizzes.
     * @return a list of QuizInfoDto representing quizzes with titles that match keyword.
     */
    public List<QuizInfoDto> findQuizzesByKeywordAndCategory(String keyword, String category) {
        log.info("Fetching quizzes with title that match keyword: {} and category: {}", keyword, category);
        List<QuizInfoDto> quizzesByKeywordAndCategory = quizRepository.findQuizzesByKeywordAndCategory(keyword, category);
        if (quizzesByKeywordAndCategory == null || quizzesByKeywordAndCategory.isEmpty()) {
            log.info("No quizzes found with titles that match keyword: {} and category: {}", keyword, category);
            return Collections.emptyList();
        }
        return quizzesByKeywordAndCategory.stream()
                .map(quiz -> new QuizInfoDto(quiz.getquizId(), quiz.getQuiz_title(), quiz.getmediaId(), quiz.getThumbnail_filepath()))
                .collect(Collectors.toList());
    }

    /**
     * Finds quizzes with titles that match keyword and matching author.
     * Limited to 24 quizzes maximum.
     *
     * @param keyword The keyword to filter quizzes.
     * @param author The author to filter quizzes.
     * @return a list of QuizInfoDto representing quizzes with titles that match keyword.
     */
    public List<QuizInfoDto> findQuizzesByKeywordAndAuthor(String keyword, String author) {
        log.info("Fetching quizzes with title that match keyword: {} and author: {}", keyword, author);
        List<QuizInfoDto> quizzesByKeywordAndAuthor = quizRepository.findQuizzesByKeywordAndAuthor(keyword, author);
        if (quizzesByKeywordAndAuthor == null || quizzesByKeywordAndAuthor.isEmpty()) {
            log.info("No quizzes found with titles that match keyword: {} and author: {}", keyword, author);
            return Collections.emptyList();
        }
        return quizzesByKeywordAndAuthor.stream()
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

    /**
     * Find the category name from its categoryId
     * 
     * @param categoryId the categoryId of the category name you want to find
     * @return String category name
     */
    public String getCategoryById(Long categoryId) {
        String category = categoryRepository.getCategoryById(categoryId);
        return category;
    }
}
