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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class QuizControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;
  
  @Test
  @WithMockUser
  public void getQuizzesCreatedByUserShouldReturnQuizzes() throws Exception {
      mockMvc.perform(MockMvcRequestBuilders.get("/api/quizzes/user/adminUser")
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(jsonPath("$[0].quizId").value(1))
              .andExpect(jsonPath("$[0].quiz_title").value("Basic Math Quiz"))
              .andExpect(jsonPath("$[0].mediaId").value(1))
              .andExpect(jsonPath("$[0].thumbnail_filepath").value("path/to/image.jpg"));
  }

  


}