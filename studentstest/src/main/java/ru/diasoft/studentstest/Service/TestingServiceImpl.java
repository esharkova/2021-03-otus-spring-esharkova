package ru.diasoft.studentstest.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.diasoft.studentstest.dao.QuestionDao;
import ru.diasoft.studentstest.domain.Answer;
import ru.diasoft.studentstest.domain.Question;
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

        //List<Answer> listAnswer = new ArrayList<Answer>(); //массив ответов

        for (int i = 0; i < questionDao.getListQuestion().size(); i++) {

            System.out.println(questionDao.getListQuestion().get(i).getQuestionnumber() + ". " + questionDao.getListQuestion().get(i).getQuestiontext());
            readerService.saveAnswer();
            //listAnswer.add(answer);
        }



        if (checkAnswers(questionDao.getListQuestion(), readerService.getAnswerList())>= Integer.parseInt(count)) {
            student.setResult(true);
        } else {
            student.setResult(false);
        }

        student.getResultText();


    }

    @Override
    public int checkAnswers(List<Question> questionList, List<Answer> answerList) {

        int countRightAnswer = 0;

        //сравнение ответа на вопрос и правильного ответа
        for (int j = 0; j < questionList.size(); j++) {
            System.out.println(questionList.get(j).getQuestiontext() + "Правильный ответ: " + questionList.get(j).getRightAnswer());
            System.out.println("Дан ответ: " + answerList.get(j).getAnswertext());
            if (answerList.get(j).getAnswertext().equals(questionList.get(j).getRightAnswer())) {
                countRightAnswer++;
                System.out.println("Правильно!");
            } else {
                System.out.println("НЕ правильно!");
            }
        }
        return countRightAnswer;
    }
}
