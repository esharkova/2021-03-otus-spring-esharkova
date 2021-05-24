package ru.diasoft.StudentsTest.dao;

import ru.diasoft.StudentsTest.domain.Answer;

import java.util.List;

public interface AnswerDao {
    Answer addAnswer(String answer);

    void saveAnswer(Answer answer);

    List<Answer> getAnswersList();

}
