package ru.diasoft.studentstest.dao;

import ru.diasoft.studentstest.domain.Answer;

public interface AnswerDao {
    Answer addAnswer(int number, String answer);
}
