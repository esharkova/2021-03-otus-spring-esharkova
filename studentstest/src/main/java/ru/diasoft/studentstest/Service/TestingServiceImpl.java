package ru.diasoft.studentstest.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.diasoft.studentstest.dao.AnswerDao;
import ru.diasoft.studentstest.dao.QuestionDao;
import ru.diasoft.studentstest.domain.Answer;
import ru.diasoft.studentstest.domain.Question;
import ru.diasoft.studentstest.domain.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestingServiceImpl implements TestingService {
    private final QuestionDao questionDao;
    private final String count;
    private final ReaderService readerService;


    public TestingServiceImpl(ReaderService readerService, QuestionDao questionDao, @Value("${count}") String count) {
        this.readerService = readerService;
        this.questionDao = questionDao;
        this.count = count;
    }

    @Override
    public void testing() throws IOException {

        Student student = readerService.getStudent();

        List<Answer> list_answer = new ArrayList<Answer>(); //массив ответов

        int count_right_answer = 0;

        for (int i = 0; i < questionDao.getList_question().size(); i++) {

            System.out.println(questionDao.getList_question().get(i).getQuestionnumber() + ". " + questionDao.getList_question().get(i).getQuestiontext());
            Answer answer = readerService.getAnswer();
            list_answer.add(answer);
        }

        //сравнение ответа на вопрос и правильного ответа
        for (int j = 0; j < questionDao.getList_question().size(); j++) {
            System.out.println(questionDao.getList_question().get(j).getQuestiontext() + "Правильный ответ: " + questionDao.getList_question().get(j).getRight_answer());
            System.out.println("Дан ответ: " + list_answer.get(j).getAnswertext());
            if (list_answer.get(j).getAnswertext().equals(questionDao.getList_question().get(j).getRight_answer())) {
                count_right_answer++;
                System.out.println("Правильно!");
            } else {
                System.out.println("НЕ правильно!");
            }
        }

        System.out.println("Итого правильных ответов " + count_right_answer);
        if (count_right_answer >= Integer.parseInt(count)) {
            student.setResult(true);
        } else {
            student.setResult(false);
        }
        System.out.println(student.getSurname() + " " + student.getName() + " " + (student.getResult() ? "Тест успешно пройден!" : "Тест не пройден!"));

    }
}
