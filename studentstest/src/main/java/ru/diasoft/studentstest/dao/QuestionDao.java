package ru.diasoft.studentstest.dao;
import ru.diasoft.studentstest.domain.Question;

public interface QuestionDao {
    Question findByNumber(int number, String question, String right_answer);
    Question addQuestion(int number, String question, String right_answer);
}
