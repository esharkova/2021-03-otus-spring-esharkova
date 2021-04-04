package ru.diasoft.studentstest.dao;
import ru.diasoft.studentstest.domain.Question;

public interface QuestionDao {
    Question findByNumber(int Number, String Question);
}
