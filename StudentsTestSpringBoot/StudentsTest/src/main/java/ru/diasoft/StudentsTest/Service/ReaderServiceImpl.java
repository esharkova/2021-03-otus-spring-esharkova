package ru.diasoft.StudentsTest.Service;

import org.springframework.cglib.core.Local;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.diasoft.StudentsTest.Config.AppConfig;
import ru.diasoft.StudentsTest.dao.AnswerDao;
import ru.diasoft.StudentsTest.dao.StudentDao;
import ru.diasoft.StudentsTest.domain.Answer;
import ru.diasoft.StudentsTest.domain.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

@Service
public class ReaderServiceImpl implements ReaderService {
    private final BufferedReader bufferedReader;
    private final StudentDao studentDao;
    private final AnswerDao answerDao;
    private final MessageSource messageSource;
    private final Locale locale;

    private Student student;

    public ReaderServiceImpl(StudentDao studentDao, AnswerDao answerDao, MessageSource messageSource, AppConfig config) {
        this.studentDao = studentDao;
        this.answerDao = answerDao;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.messageSource = messageSource;
        this.locale = config.getLocale();
    }


    @Override
    public void saveAnswer() throws IOException {

        Answer answer = answerDao.addAnswer(bufferedReader.readLine());
        answerDao.saveAnswer(answer);

    }

    @Override
    public List<Answer> getAnswerList() {
        return answerDao.getAnswersList();
    }

    @Override
    public Student getStudent() throws IOException{

        if (student == null){

        System.out.println(messageSource.getMessage("strings.surname", null, locale));
        String surname = bufferedReader.readLine();
        System.out.println(messageSource.getMessage("strings.name", null, locale));
        String name = bufferedReader.readLine();

        student =  studentDao.addStudent(surname,name);}

        return student;

    }


    @Override
    public String saveStudent(String surname, String name) {
        student =  studentDao.addStudent(surname,name);
        return messageSource.getMessage("strings.welcome", null, locale) + student.getSurname() + " " + student.getName();
    }
}
