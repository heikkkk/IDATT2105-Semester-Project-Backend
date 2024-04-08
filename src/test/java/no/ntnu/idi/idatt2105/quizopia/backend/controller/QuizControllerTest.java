package no.ntnu.idi.idatt2105.quizopia.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@ActiveProfiles("test") 
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void postNewQuizWithNewQuestionsAndNewAnswersWillReturnQuiz() throws Exception {
        String jsonBody = "{\n" +
            "  \"quizId\": -25,\n" +
            "  \"title\": \"Test Quiz\",\n" +
            "  \"description\": \"Test Quiz\",\n" +
            "  \"isPublic\": true,\n" +
            "  \"createdAt\": null,\n" +
            "  \"templateId\": null,\n" +
            "  \"categoryId\": null,\n" +
            "  \"mediaId\": null,\n" +
            "  \"userId\": 1,\n" +
            "  \"questions\": [\n" +
            "    {\n" +
            "      \"questionId\": -14,\n" +
            "      \"questionName\": \"Test Question\",\n" +
            "      \"questionText\": \"What is a test question\",\n" +
            "      \"explanations\": \"The answer is 42.\",\n" +
            "      \"questionDuration\": 30,\n" +
            "      \"isPublic\": true,\n" +
            "      \"typeId\": null,\n" +
            "      \"difficultyId\": null,\n" +
            "      \"mediaId\": null,\n" +
            "      \"answers\": [\n" +
            "        {\n" +
            "          \"answerId\": -14,\n" +
            "          \"answerText\": \"42\",\n" +
            "          \"isCorrect\": true\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/quizzes/updateQuiz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser
    public void postQuizThatAlreadyExistsWillUpdateIt() throws Exception {
        String jsonBody = "{\n" +
            "  \"quizId\": 1,\n" + 
            "  \"title\": \"Already Exists\",\n" +
            "  \"description\": \"Already Exists\",\n" +
            "  \"isPublic\": true,\n" +
            "  \"createdAt\": null,\n" +
            "  \"templateId\": null,\n" +
            "  \"categoryId\": null,\n" +
            "  \"mediaId\": null,\n" +
            "  \"userId\": 1,\n" +
            "  \"questions\": [\n" +
            "    {\n" +
            "      \"questionId\": 1,\n" +
            "      \"questionName\": \"Test Question\",\n" +
            "      \"questionText\": \"What is a test question\",\n" +
            "      \"explanations\": \"The answer is 42.\",\n" +
            "      \"questionDuration\": 30,\n" +
            "      \"isPublic\": true,\n" +
            "      \"typeId\": null,\n" +
            "      \"difficultyId\": null,\n" +
            "      \"mediaId\": null,\n" +
            "      \"answers\": [\n" +
            "        {\n" +
            "          \"answerId\": -14,\n" +
            "          \"answerText\": \"42\",\n" +
            "          \"isCorrect\": true\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/quizzes/updateQuiz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test
    @WithMockUser
    public void getQuizzesCreatedByUserShouldReturnQuizzes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/quizzes/user/adminUser")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].quizId").exists())
                .andExpect(jsonPath("$[1].quizId").exists())
                .andExpect(jsonPath("$[2].quizId").exists())
                .andExpect(jsonPath("$[3].quizId").exists());
    }

    @Test
    @WithMockUser
    public void getPublicQuizzesShouldReturnPublicQuizzes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/quizzes/public")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quizId").exists()) 
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].quizId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].quizId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].quizId").exists()); 
    }

    @Test
    @WithMockUser
    public void getQuizByIdReturnsCorrectQuiz() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/quizzes/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quizId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Basic Math Quiz"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("A quiz covering basic math principles."));
    }

    @Test
    @WithMockUser
    public void getCategoryByIdReturnsCorrectCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/quizzes/id/category/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Science"));
    }
                
    

    @Test
    @WithMockUser
    public void getQuizWithKeywordInTitleAndCorrectAuthorNameGivesCorrectQuizBack() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/quizzes/keyword/History/author/adminUser")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].quizId").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].categoryId").value(3))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("adminUser"));
    }
}
