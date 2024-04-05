package no.ntnu.idi.idatt2105.quizopia.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest()
@TestPropertySource(locations="classpath:application-test.yaml")
@ActiveProfiles("test") 
class QuizopiaApplicationTests {

	@Test
	void contextLoads() {
	}

}
