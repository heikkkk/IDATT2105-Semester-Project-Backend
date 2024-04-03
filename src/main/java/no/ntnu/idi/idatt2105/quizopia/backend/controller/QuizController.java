package no.ntnu.idi.idatt2105.quizopia.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizDto;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizInfoDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;
import no.ntnu.idi.idatt2105.quizopia.backend.service.QuizService;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



/**
 * Controller for managing quizzes.
 */
@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class QuizController {

    private final QuizService quizService;

    /**
     * Creates a new quiz.
     *
     * @param quizDto the quiz DTO containing data for the new quiz
     * @return ResponseEntity with the created Quiz and HTTP status
     */
    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizDto quizDto) { 
        log.info("Creating a new quiz with title: {}", quizDto.getTitle());
        Quiz createdQuiz = quizService.createQuiz(quizDto);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest() 
            .path("/{id}") 
            .buildAndExpand(createdQuiz.getQuizId()) 
            .toUri(); 
        log.info("Quiz created successfully with ID: {}", createdQuiz.getQuizId());
        return ResponseEntity.created(location).body(createdQuiz); 
        
    }

    @PutMapping("updateQuiz")
    public ResponseEntity<Quiz> updateQuiz(@RequestBody QuizDto quizDto) {
        log.info("Updating existing quiz with ID: {}", quizDto.getquizId());
        Quiz updatedQuiz = quizService.updateQuiz(quizDto);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest() 
            .path("/{id}") 
            .buildAndExpand(updatedQuiz.getQuizId()) 
            .toUri(); 
        log.info("Quiz updated successfully with ID: {}", updatedQuiz.getQuizId());
        return ResponseEntity.created(location).body(updatedQuiz); 
    }

    /**
     * Retrieves all quizzes created by a specific user.
     *
     * @param username the username of the user whose quizzes are to be retrieved
     * @return ResponseEntity with a list of quizzes or no content status
     */
    @GetMapping("/user/{username}")
    public ResponseEntity<List<QuizInfoDto>> getQuizzesCreatedByUser(@PathVariable String username) {
        log.info("Fetching quizzes created by user: {}", username);
        List<QuizInfoDto> quizzesCreatedByUserList = quizService.findQuizzesCreatedByUserId(username);
        if (quizzesCreatedByUserList.isEmpty()) {
            log.info("No quizzes found for user: {}", username);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quizzesCreatedByUserList);
    }

    /**
     * Retrieves all public quizzes.
     *
     * @return ResponseEntity with a list of public quizzes or no content status
     */
    @GetMapping("/public")
    public ResponseEntity<List<QuizInfoDto>> getPublicQuizzes() {
        log.info("Fetching all public quizzes");
        List<QuizInfoDto> publicQuizzes = quizService.findPublicQuizzes();
        if (publicQuizzes.isEmpty()) {
            log.info("No public quizzes found");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(publicQuizzes);
    }

    /**
     * Retrieves quizzes belonging to a specific category.
     *
     * @param category the category of the quizzes to retrieve
     * @return ResponseEntity with a list of quizzes or no content status
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<QuizInfoDto>> getQuizzesByCategory(@PathVariable String category) {
        log.info("Fetching quizzes in category: {}", category);
        List<QuizInfoDto> quizzes = quizService.findQuizzesByCategory(category);
        if (quizzes.isEmpty()) {
            log.info("No quizzes found in category: {}", category);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quizzes);
    }

    /**
     * Retrieves a specific quiz by its ID.
     * @param quizId The ID of the quiz.
     * @return The requested quiz if found.
     */
    @GetMapping("quiz/{quizId}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable Long quizId) {
        log.info("Fetching quiz with ID: {}", quizId);
        QuizDto quizDto = quizService.getQuizById(quizId);
        if (quizDto == null) {
            log.info("Quiz not found with ID: {}", quizId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quizDto);
    }

    /**
     * Retrieves a specific category name by its ID.
     * @param quizId The ID of the category.
     * @return The requested category name if found.
     */
    @GetMapping("id/category/{categoryId}")
    public ResponseEntity<String> getCategoryById(@PathVariable Long categoryId) {
        log.info("Fetching category with ID: {}", categoryId);
        String category = quizService.getCategoryById(categoryId);
        if (category == null) {
            log.info("Category not found with ID: {}", categoryId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }
}
