package ru.diasoft.StudentsTest.dao;

import org.springframework.stereotype.Component;
import ru.diasoft.StudentsTest.domain.Student;

@Component
public class StudentDaoSimple implements StudentDao {
    public Student addStudent(String surname, String name){
        return new Student(surname, name, false);
    }
}
