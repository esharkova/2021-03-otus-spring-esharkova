package ru.diasoft.studentstest.dao;

import ru.diasoft.studentstest.domain.Student;

public interface StudentDao {
    Student addStudent(String surname, String name);
}
