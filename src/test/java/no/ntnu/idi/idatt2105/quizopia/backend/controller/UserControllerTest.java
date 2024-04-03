package no.ntnu.idi.idatt2105.quizopia.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  @WithMockUser
  void shouldGetUsernameAdminUserByUserId() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/users/get-name/1"))
        .andExpect(MockMvcResultMatchers.content().string("adminUser"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithMockUser
  void shouldGetAdminUserIdByUserName() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/api/users/get-id/adminUser"))
        .andExpect(MockMvcResultMatchers.content().string("1"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}