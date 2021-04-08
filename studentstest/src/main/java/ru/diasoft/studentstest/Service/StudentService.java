package ru.diasoft.studentstest.Service;

import ru.diasoft.studentstest.domain.Student;

public interface StudentService {
    Student createStudent(String surname, String name);
}
