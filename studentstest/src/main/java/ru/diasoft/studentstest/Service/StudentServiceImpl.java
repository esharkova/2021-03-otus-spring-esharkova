package ru.diasoft.studentstest.Service;

import org.springframework.stereotype.Service;
import ru.diasoft.studentstest.dao.StudentDao;
import ru.diasoft.studentstest.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentDao dao;

    public StudentServiceImpl(StudentDao dao){
        this.dao = dao;
    }

    public Student createStudent(String surname, String name) {
        return dao.addStudent(surname, name);
    }
}
