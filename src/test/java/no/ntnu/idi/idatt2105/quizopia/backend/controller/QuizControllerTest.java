package no.ntnu.idi.idatt2105.quizopia.backend.controller;

import java.util.ArrayList;

import no.ntnu.idi.idatt2105.quizopia.backend.dto.AnswerDto;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuestionDto;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class QuizControllerTest {

  @Autowired
  private MockMvc mockMvc;

  // @Autowired
  // private ObjectMapper objectMapper;
  
  @Test
  @WithMockUser
  public MvcResult createQuizShouldReturnQuizEntity() throws Exception {
    // Create AnswerDto objects
    AnswerDto answer1 = new AnswerDto("Answer 1", true);
    AnswerDto answer2 = new AnswerDto("Answer 2", false);

    // Create QuestionDto object
    QuestionDto questionDto = new QuestionDto();
    questionDto.setQuestionName("Sample Question Name");
    questionDto.setQuestionText("What is the capital of France?");
    questionDto.setExplanations("Paris is the capital of France.");
    questionDto.setQuestion_duration(30);
    questionDto.setIsPublic(true);
    questionDto.settypeId(1L);
    questionDto.setDifficultyId(2L);
    questionDto.setMediaId(3L);
    List<AnswerDto> answers = new ArrayList<>();
    answers.add(answer1);
    answers.add(answer2);
    questionDto.setAnswers(answers);

    // Create QuizDTO object
    QuizDto quizDto = new QuizDto();
    quizDto.setTitle("Sample Quiz");
    quizDto.setDescription("A sample quiz description.");
    quizDto.setIsPublic(true);
    quizDto.setCreated_at(LocalDateTime.now());
    quizDto.setTemplateId(1L);
    quizDto.setCategoryId(2L);
    quizDto.setMediaId(3L);
    quizDto.setuserId(4L);
    List<QuestionDto> questions = new ArrayList<>();
    questions.add(questionDto);
    quizDto.setQuestions(questions);

    return mockMvc.perform(MockMvcRequestBuilders.post("/api/quizzes")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer dummy_token"))
        .andReturn();    
  }
}