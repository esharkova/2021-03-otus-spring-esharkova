package ru.diasoft.studentstest.Service;

import ru.diasoft.studentstest.domain.Answer;
import ru.diasoft.studentstest.domain.Student;

import java.io.IOException;

public interface ReaderService {
    Answer getAnswer() throws IOException;

    Student getStudent() throws IOException;

    void close() throws IOException;
}
