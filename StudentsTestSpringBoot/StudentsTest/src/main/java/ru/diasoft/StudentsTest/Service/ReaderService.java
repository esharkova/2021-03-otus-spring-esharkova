package ru.diasoft.StudentsTest.Service;

import ru.diasoft.StudentsTest.domain.Answer;
import ru.diasoft.StudentsTest.domain.Student;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

public interface ReaderService {
    void saveAnswer() throws IOException;

    Student getStudent() throws IOException;

    String saveStudent(String surname, String name);

    List<Answer> getAnswerList();
}
