package ru.diasoft.studentstest.dao;

import ru.diasoft.studentstest.domain.Answer;

import java.util.List;

public interface AnswerDao {
    Answer addAnswer(String answer);

    void saveAnswer(Answer answer);

    List<Answer> getAnswersList();
}
