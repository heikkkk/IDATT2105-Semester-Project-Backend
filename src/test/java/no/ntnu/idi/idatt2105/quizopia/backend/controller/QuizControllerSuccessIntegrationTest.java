package no.ntnu.idi.idatt2105.quizopia.backend.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

// Your base configuration class that configures the Spring Boot application for testing
import no.ntnu.idi.idatt2105.quizopia.backend.config.TestConfig;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizDto;

@Transactional
@ActiveProfiles("test")
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(scripts = {"/initial_data_1.0_setup.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class QuizControllerSuccessIntegrationTest {

  @Autowired
  private MockMvc mockMvc; 

  @Autowired
  private ObjectMapper objectMapper;

  @Nested
  @DisplayName("Integration Test for Quiz Entity")
  class QuizEntityTest {

    @Test
    @WithMockUser // Mocks a user for this test, bypassing the need for a real JWT
    @Sql(scripts = "initial_data_1.0_setup.sql") // SQL script to set up test data specific to this test
    @DisplayName("Test creating or handling a Quiz entity success")
    public void testQuizEntityHandling() throws Exception {
        // Assume we're sending a POST request to create a new Quiz
        // The specifics of your Quiz entity will determine what goes into the request body

        LocalDateTime localDateTime = LocalDateTime.of(2024, 4, 1, 12, 0);
        QuizDto testQuizDto = new QuizDto(
            "General Knowledge Quiz", // title
            "A quiz covering various topics of general interest.", // description
            true, // isPublic
            localDateTime, // createdAt
            1L, // media_id
            2L, // category_id
            3L, // template_id
            4L, // user_id
            null // questions list
        );     
        
        // Convert QuizDto to JSON string
        String quizDtoJson = objectMapper.writeValueAsString(testQuizDto);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(quizDtoJson);

        MvcResult result = mockMvc
                .perform(mockRequest)
                .andExpect(status().isOk())
                .andReturn();

        // Assertions to verify the response
        // This part depends on what your API returns upon successful creation or handling of a Quiz
        String responseContent = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        // For example, you might want to deserialize the response content and assert the Quiz properties
        
        QuizDto actualResponse = objectMapper.readValue(responseContent, QuizDto.class);

        
        // Example assertion - adjust according to your actual response
        assertEquals("Expected Quiz Title", actualResponse.getTitle());
        assertEquals("Expected Description", actualResponse.getDescription());
        assertEquals(200, result.getResponse().getStatus());
        // Further assertions about the response can be added here
    }
  }
}
