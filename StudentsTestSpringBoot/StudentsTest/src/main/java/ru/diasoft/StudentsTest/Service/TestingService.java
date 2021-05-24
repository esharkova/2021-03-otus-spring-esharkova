package ru.diasoft.StudentsTest.Service;

import ru.diasoft.StudentsTest.domain.Answer;
import ru.diasoft.StudentsTest.domain.Question;

import java.io.IOException;
import java.util.List;

public interface TestingService {
    void testing() throws IOException;
    int checkAnswers(List<Question> questionList, List<Answer> answerList);
}
