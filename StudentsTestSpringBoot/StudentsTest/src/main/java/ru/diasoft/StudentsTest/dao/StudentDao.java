package ru.diasoft.StudentsTest.dao;

import ru.diasoft.StudentsTest.domain.Student;

public interface StudentDao {
    Student addStudent(String surname, String name);
}
