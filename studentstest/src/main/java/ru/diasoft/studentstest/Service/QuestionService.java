package ru.diasoft.studentstest.Service;

import ru.diasoft.studentstest.domain.Question;

public interface QuestionService {
    Question getByNumber(int number, String question, String right_answer);
    Question createQuestion (int number, String question, String right_answer);
}
