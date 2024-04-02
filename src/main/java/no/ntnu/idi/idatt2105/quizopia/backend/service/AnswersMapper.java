package no.ntnu.idi.idatt2105.quizopia.backend.service;

import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.AnswersDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Answers;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswersMapper {

    public Answers toAnswers(AnswersDto answersDto) {
        Answers answers = new Answers();
        answers.setAnswerText(answersDto.getAnswerText());
        return answers;
    }
}
