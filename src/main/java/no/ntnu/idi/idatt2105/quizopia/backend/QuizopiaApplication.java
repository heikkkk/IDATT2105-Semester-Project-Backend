package no.ntnu.idi.idatt2105.quizopia.backend;

import no.ntnu.idi.idatt2105.quizopia.backend.config.RSAKeyRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RSAKeyRecord.class)
@SpringBootApplication
public class QuizopiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizopiaApplication.class, args);
	}

}
