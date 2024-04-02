package no.ntnu.idi.idatt2105.quizopia.backend.controller;

import no.ntnu.idi.idatt2105.quizopia.backend.dto.QuizDto;
import no.ntnu.idi.idatt2105.quizopia.backend.service.QuizService;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.context.support.WithMockUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = QuizController.class)
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
public class QuizCreationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService; 

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username="userAdmin", roles={"USER","ADMIN"})
    public void testCreateQuiz() throws Exception {
        QuizDto quizDto = new QuizDto(
            "Title",
            "Description",
            true,
            LocalDateTime.now(),
            1L,
            1L,
            1L,
            1L,
            Collections.emptyList()
        );       mockMvc.perform(post("/api/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quizDto))
                .with(SecurityMockMvcRequestPostProcessors.csrf())) 
                .andExpect(status().isOk());
    }
}
