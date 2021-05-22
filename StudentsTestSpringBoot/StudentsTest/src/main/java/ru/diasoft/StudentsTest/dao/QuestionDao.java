package ru.diasoft.StudentsTest.dao;

import ru.diasoft.StudentsTest.domain.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionDao {
    //Question findByNumber(int number, String question, String rightAnswer);
    Question addQuestion(int number, String question, String rightAnswer);
    List<Question> getListQuestion() throws IOException;
}
