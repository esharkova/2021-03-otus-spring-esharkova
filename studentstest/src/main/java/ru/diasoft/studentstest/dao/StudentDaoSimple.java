package ru.diasoft.studentstest.dao;

import org.springframework.stereotype.Component;
import ru.diasoft.studentstest.domain.Student;

@Component
public class StudentDaoSimple implements StudentDao {
    public Student addStudent(String surname, String name){
        return new Student(surname, name);
    }
}
