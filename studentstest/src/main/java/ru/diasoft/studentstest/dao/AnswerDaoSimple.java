package ru.diasoft.studentstest.dao;

import org.springframework.stereotype.Component;
import ru.diasoft.studentstest.domain.Answer;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnswerDaoSimple implements AnswerDao{

    List<Answer> answerList = new ArrayList<>();

    @Override
    public Answer addAnswer(String answer) {
        return new Answer(answer);
    }

    @Override
    public void saveAnswer(Answer answer) {

        answerList.add(answer);
    }

    @Override
    public List<Answer> getAnswersList() {
        return answerList;
    }

}
