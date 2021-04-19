package ru.diasoft.studentstest.Service;

import org.springframework.stereotype.Service;
import ru.diasoft.studentstest.dao.AnswerDao;
import ru.diasoft.studentstest.dao.StudentDao;
import ru.diasoft.studentstest.domain.Answer;
import ru.diasoft.studentstest.domain.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ReaderServiceImpl implements ReaderService{
    private final BufferedReader bufferedReader;
    private final StudentDao studentDao;
    private final AnswerDao answerDao;


    public ReaderServiceImpl(StudentDao studentDao, AnswerDao answerDao) {
        this.studentDao = studentDao;
        this.answerDao = answerDao;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public Answer getAnswer() throws IOException {

        Answer answer = answerDao.addAnswer(bufferedReader.readLine());
        return answer;

    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }

    @Override
    public Student getStudent() throws IOException{

        System.out.println("Enter your surname");
        String surname = bufferedReader.readLine();
        System.out.println("Enter your name");
        String name = bufferedReader.readLine();

        Student student =  studentDao.addStudent(surname,name);

        return student;

    }

}
