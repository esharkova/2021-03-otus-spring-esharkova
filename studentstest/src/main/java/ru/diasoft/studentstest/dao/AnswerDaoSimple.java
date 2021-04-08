package ru.diasoft.studentstest.dao;

import org.springframework.stereotype.Component;
import ru.diasoft.studentstest.domain.Answer;

@Component
public class AnswerDaoSimple implements AnswerDao{

    public Answer addAnswer(int number, String answer) {
        return new Answer(number, answer);
    }

}
