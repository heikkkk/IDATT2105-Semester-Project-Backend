package no.ntnu.idi.idatt2105.quizopia.backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Base64;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.UserRegistrationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test") 
class AuthenticationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private MvcResult generateAuthResponse() throws Exception {
    String username = "adminUser";
    String password = "password123";
    String credentials = username + ":" + password;
    String base64Credentials = new String(Base64.getEncoder().encode(credentials.getBytes()));

    return mockMvc.perform(MockMvcRequestBuilders.post("/sign-in")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials))
        .andReturn();
  }

  @Test
  void shouldAuthenticateUserAndGenerateAccessToken() throws Exception {

    String username = "adminUser";
    String password = "password123";
    String credentials = username + ":" + password;
    String base64Credentials = new String(Base64.getEncoder().encode(credentials.getBytes()));

   mockMvc.perform(MockMvcRequestBuilders.post("/sign-in")
           .contentType(MediaType.APPLICATION_JSON)
           .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials))
       .andExpect(MockMvcResultMatchers.status().isOk())
       .andExpect(jsonPath("$.access_token").exists())
       .andExpect(jsonPath("$.access_token_expiry").exists())
       .andExpect(jsonPath("$.token_type").exists())
       .andExpect(jsonPath("$.user_name").exists())
       .andExpect(MockMvcResultMatchers.cookie().exists("refresh_token"));
  }

  @Test
  void shouldGetAccessTokenFromRefreshToken() throws Exception {
    MvcResult authResponse = generateAuthResponse();
    String refreshToken = authResponse.getResponse().getCookie("refresh_token").getValue();

    mockMvc.perform(MockMvcRequestBuilders.post("/refresh-token")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.access_token").exists())
        .andExpect(jsonPath("$.access_token_expiry").exists())
        .andExpect(jsonPath("$.token_type").exists())
        .andExpect(jsonPath("$.user_name").exists());
  }

  @Test
  void shouldRegisterUser() throws Exception {
    UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
        "test_User", "password","test@email.com", 2L);

    mockMvc.perform(MockMvcRequestBuilders.post("/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userRegistrationDto)))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void shouldNotRegisterUserWithIncorrectDto() throws Exception {
    ObjectNode jsonObject = objectMapper.createObjectNode();

    jsonObject.put("wrongUsername", "username");
    jsonObject.put("wrongPassword", "password");
    jsonObject.put("wrongEmail", "wrong@email.com");
    jsonObject.put("wrongRoleId", 123);

    String jsonString = jsonObject.toString();
    mockMvc.perform(MockMvcRequestBuilders.post("/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonString))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}