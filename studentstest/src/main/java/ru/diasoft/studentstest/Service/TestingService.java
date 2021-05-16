package ru.diasoft.studentstest.Service;

import ru.diasoft.studentstest.domain.Answer;
import ru.diasoft.studentstest.domain.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface TestingService {
    void testing() throws IOException;
    int checkAnswers(List<Question> questionList, List<Answer> answerList);

}
