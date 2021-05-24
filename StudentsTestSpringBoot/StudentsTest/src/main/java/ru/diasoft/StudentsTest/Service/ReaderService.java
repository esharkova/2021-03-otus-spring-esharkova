package ru.diasoft.StudentsTest.Service;

import ru.diasoft.StudentsTest.domain.Answer;
import ru.diasoft.StudentsTest.domain.Student;

import java.io.IOException;
import java.util.List;

public interface ReaderService {
    void saveAnswer() throws IOException;

    Student getStudent() throws IOException;

    //void close() throws IOException;

    //void addAnswer(Answer answer);

    List<Answer> getAnswerList();
}
