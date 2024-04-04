package no.ntnu.idi.idatt2105.quizopia.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizDto;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizInfoDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;
import no.ntnu.idi.idatt2105.quizopia.backend.service.quiz.QuizService;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


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

    @Operation(
        summary = "Create a quiz"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully created a new quiz",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Quiz.class)
                )
            }
            ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid quiz details or problems storing corresponding questions and "
                + "answers",
            content = @Content
        )
    })
    @Parameter(
        name = "QuizDto",
        description = "QuizDto containing all the information relation to a quiz",
        content = {
            @Content(mediaType = "application/json",
                schema = @Schema(implementation = QuizDto.class))
        })
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

    @Operation(
        summary = "Update a quiz"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully updated the specified quiz",
            content = {
                @Content(mediaType = "application/json",
                schema = @Schema(implementation = Quiz.class))
            }
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Failed to update quiz",
            content = @Content
        )
    })
    @Parameter(
        name = "QuizDto",
        description = "QuizDto containing all the information relation to a quiz",
        required = true,
        content = {
            @Content(mediaType = "application/json",
                schema = @Schema(implementation = QuizDto.class))
        }
    )
    @PostMapping("/updateQuiz")
    public ResponseEntity<Quiz> saveOrUpdateQuiz(
        @RequestBody QuizDto quizDto
    ) {
        if(quizDto.getquizId() >= 0) {
            log.info("Saving a new quiz");
        } else {
            log.info("Updating existing quiz with ID: {}", quizDto.getquizId());
        }
        Quiz updatedQuiz = quizService.saveOrUpdateQuiz(quizDto);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest() 
            .path("/{id}") 
            .buildAndExpand(updatedQuiz.getQuizId()) 
            .toUri(); 
        log.info("Quiz updated/saved successfully with ID: {}", updatedQuiz.getQuizId());
        return ResponseEntity.created(location).body(updatedQuiz); 
    }

    

    @Operation(
        summary = "Get quizzes associated with a user",
        description = "Get all the quizzes associated with a user by providing the username as "
            + "the path variable")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved quizzes associated with a user",
            content = {
                @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = QuizInfoDto.class)))}
        ),
        @ApiResponse(
            responseCode = "204",
            description = "No content, could not find any quizzes associated with the provided "
                + "user or the user could not be found",
            content = @Content
        )
    })
    @Parameter(
        name = "Username",
        description = "The name of the user associated with the quizzes to be found",
        required = true,
        example = "adminUser"
    )
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

    @Operation(
        summary = "Get all public quizzes",
        description = "Get all the publicly available quizzes registered in the database"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully got all the public quizzes",
            content = {
                @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = QuizInfoDto.class)))}
        ),
        @ApiResponse(
            responseCode = "204",
            description = "Could not find any public quizzes registered in the database",
            content = @Content
        )
    })
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

    @Operation(
        summary = "Get quizzes by category",
        description = "Get all quizzes associated with a category"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all the quizzes associated with the given "
                + "category",
            content = {
                @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = QuizInfoDto.class)))}
        ),
        @ApiResponse(
            responseCode = "204",
            description = "Could not find any quizzes registered in the database with the given "
                + "category",
            content = @Content
        )
    })
    @Parameter(
        name = "Category",
        description = "The category of the quizzes to be found",
        required = true,
        example = "History"
    )
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

    @Operation(summary = "Get quiz by ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully got the quiz",
            content = {
                @Content(mediaType = "application/json",
                schema = @Schema(implementation = QuizDto.class))}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Could not find the user with the provided ID",
            content = @Content
        )
    })
    @Parameter(
        name = "Quiz-ID",
        description = "The ID of the quiz to be found",
        required = true,
        example = "1"
    )
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable Long quizId) {
        log.info("Fetching quiz with ID: {}", quizId);
        QuizDto quizDto = quizService.getQuizById(quizId);
        if (quizDto == null) {
            log.info("Quiz not found with ID: {}", quizId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quizDto);
    }

    @Operation(summary = "Get category by category-ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the name of the category associated with the "
                + "provided ID",
            content = {
                @Content(mediaType = "text/plain",
                schema = @Schema(implementation = String.class))}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Could not find any categories associated with the given ID"
        )
    })
    @Parameter(
        name = "Category-ID",
        description = "The ID of the Category to be found",
        required = true,
        example = "1"
    )
    @GetMapping("/id/category/{categoryId}")
    public ResponseEntity<String> getCategoryById(@PathVariable Long categoryId) {
        log.info("Fetching category with ID: {}", categoryId);
        String category = quizService.getCategoryById(categoryId);
        if (category == null) {
            log.info("Category not found with ID: {}", categoryId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    @Operation(
        summary = "Get quizzes by keyword",
        description = "Get all the quizzes with titles that match the keyword provided in the "
            + "path variable")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all quizzes with titles that contain the "
                + "provided keyword",
            content = {
                @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = QuizInfoDto.class))
                )
            }
        ),
        @ApiResponse(
            responseCode = "204",
            description = "No quizzes found with titles that contain the provided keyword",
            content = @Content
        )
    })
    @Parameter(
        name = "Keyword",
        description = "The keyword in the title of the quizzes to be found",
        required = true,
        example = "Monkey"
    )
    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<QuizInfoDto>> getQuizzesByKeyword(@PathVariable String keyword) {
        log.info("Fetching quizzes with titles that contain: {}", keyword);
        List<QuizInfoDto> quizzes = quizService.findQuizzesByKeyword(keyword);
        if (quizzes.isEmpty()) {
            log.info("No quizzes with titles that contain: {}", keyword);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quizzes);
    }

    @Operation(
        summary = "Get quizzes by keyword and category",
        description = "Get all of the quizzes with both the same category as the one provided in "
            + "the path variable, and which has a title containing the keyword provided in the "
            + "path variable"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the quizzes with titles containing the provided"
                + " keyword, and categories matching the category provided",
            content = {
                @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = QuizInfoDto.class))
                )
            }
        ),
        @ApiResponse(
            responseCode = "204",
            description = "Could not find any quizzes with a title containing either the provided "
                + "keyword or matching the provided category",
            content = @Content
        )
    })
    @Parameters(value = {
        @Parameter(
            name = "Keyword",
            description = "The keyword in the title of the quizzes to be found",
            required = true,
            example = "Monkey"
        ),
        @Parameter(
            name = "Category",
            description = "The category of the quizzes to be found",
            required = true,
            example = "History"
        )
    })
    @GetMapping("/keyword/{keyword}/category/{category}")
    public ResponseEntity<List<QuizInfoDto>> getQuizzesByKeywordAndCategory(@PathVariable String keyword, @PathVariable String category) {
        log.info("Fetching quizzes with titles that contain: {} and category: {}", keyword, category);
        List<QuizInfoDto> quizzes = quizService.findQuizzesByKeywordAndCategory(keyword, category);
        if (quizzes.isEmpty()) {
            log.info("No quizzes with titles that contain: {} and category: {}", keyword, category);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quizzes);
    }

    @Operation(
        summary = "Get quizzes by keyword and authors",
        description = "Get all of the quizzes with both the same category as the one provided in "
            + "the path variable, and which has been published by the author provided in the "
            + "path variable"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the quizzes with titles containing the provided"
                + " keyword, and author matching the author provided",
            content = {
                @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = QuizInfoDto.class))
                )
            }
        ),
        @ApiResponse(
            responseCode = "204",
            description = "Could not find any quizzes with a title containing either the provided "
                + "keyword or matching the provided author",
            content = @Content
        )
    })
    @Parameters(value = {
        @Parameter(
            name = "Keyword",
            description = "The keyword in the title of the quizzes to be found",
            required = true,
            example = "Monkey"
        ),
        @Parameter(
            name = "Author",
            description = "The name of the author to be found",
            required = true,
            example = "userAdmin"
        )
    })
    @GetMapping("/keyword/{keyword}/author/{author}")
    public ResponseEntity<List<QuizInfoDto>> getQuizzesByKeywordAndAuthor(@PathVariable String keyword, @PathVariable String author) {
        log.info("Fetching quizzes with titles that contain: {} and author: {}", keyword, author);
        List<QuizInfoDto> quizzes = quizService.findQuizzesByKeywordAndAuthor(keyword, author);
        if (quizzes.isEmpty()) {
            log.info("No quizzes with titles that contain: {} and author: {}", keyword, author);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quizzes);
    }

    // TODO ADD SWAGGER
    @PutMapping("delete-quiz/{quizId}")
    public ResponseEntity<Void> putDeleteQuizById(@PathVariable Long quizId) {
        Boolean quizDeletedSuccessfully = quizService.deleteQuizById(quizId);
        if (quizDeletedSuccessfully) {
            return ResponseEntity.ok().build(); 
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); 
        }
    }
}