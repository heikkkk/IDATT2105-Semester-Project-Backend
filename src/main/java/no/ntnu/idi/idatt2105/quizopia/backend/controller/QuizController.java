package no.ntnu.idi.idatt2105.quizopia.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizDto;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizzesCreatedByUserDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Quiz;
import no.ntnu.idi.idatt2105.quizopia.backend.service.QuizService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
@Slf4j
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizDto quizDto) { 
        Quiz createdQuiz = quizService.createQuiz(quizDto);
        return ResponseEntity.ok(createdQuiz);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<QuizzesCreatedByUserDto>> getQuizzesCreatedByUser(@PathVariable String username) {
        List<QuizzesCreatedByUserDto> quizzesCreatedByUserList = quizService.findQuizzesCreatedByUserId(username);
        if (quizzesCreatedByUserList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quizzesCreatedByUserList);
    }

    @GetMapping("/public")
    public ResponseEntity<List<QuizzesCreatedByUserDto>> getPublicQuizzes() {
        List<QuizzesCreatedByUserDto> publicQuizzes = quizService.findPublicQuizzes();
        if (publicQuizzes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(publicQuizzes);
    }
}
