package ru.diasoft.studentstest.dao;
import ru.diasoft.studentstest.domain.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface QuestionDao {
    Question findByNumber(int number, String question, String right_answer);
    Question addQuestion(int number, String question, String right_answer);
    List<Question> getList_question() throws IOException;

}
