package ru.diasoft.studentstest.Service;

import ru.diasoft.studentstest.domain.Answer;
import ru.diasoft.studentstest.domain.Student;

import java.io.IOException;
import java.util.List;

public interface ReaderService {
    //Answer getAnswer() throws IOException;
    void saveAnswer() throws IOException;

    Student getStudent() throws IOException;

    void close() throws IOException;

    void addAnswer(Answer answer);

    List<Answer> getAnswerList();
}
