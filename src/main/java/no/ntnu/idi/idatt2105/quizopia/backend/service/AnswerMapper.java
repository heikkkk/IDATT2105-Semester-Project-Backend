package no.ntnu.idi.idatt2105.quizopia.backend.service;

import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.AnswerDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Answer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerMapper {

    public Answer toAnswer(AnswerDto answerDto) {
        Answer answer = new Answer();
        answer.setAnswerText(answerDto.getAnswerText());
        return answer;
    }
}
