package ru.diasoft.studentstest.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.diasoft.studentstest.dao.QuestionDao;
import ru.diasoft.studentstest.domain.Answer;
import ru.diasoft.studentstest.domain.Student;

import java.io.IOException;
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

        List<Answer> listAnswer = new ArrayList<Answer>(); //массив ответов

        int count_right_answer = 0;

        for (int i = 0; i < questionDao.getListQuestion().size(); i++) {

            System.out.println(questionDao.getListQuestion().get(i).getQuestionnumber() + ". " + questionDao.getListQuestion().get(i).getQuestiontext());
            Answer answer = readerService.getAnswer();
            listAnswer.add(answer);
        }

        //сравнение ответа на вопрос и правильного ответа
        for (int j = 0; j < questionDao.getListQuestion().size(); j++) {
            System.out.println(questionDao.getListQuestion().get(j).getQuestiontext() + "Правильный ответ: " + questionDao.getListQuestion().get(j).getRight_answer());
            System.out.println("Дан ответ: " + listAnswer.get(j).getAnswertext());
            if (listAnswer.get(j).getAnswertext().equals(questionDao.getListQuestion().get(j).getRight_answer())) {
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
        System.out.println(student.getResultText());
        //System.out.println(student.getSurname() + " " + student.getName() + " " + (student.getResult() ? "Тест успешно пройден!" : "Тест не пройден!"));

    }
}
